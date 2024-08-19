package com.myrran.domain.mobs.common

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.ENEMY_SENSOR
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.SPELL
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.WALLS
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.mob.Enemy
import com.myrran.domain.mobs.spells.form.Form
import com.myrran.domain.mobs.spells.spell.Spell
import ktx.box2d.RayCast
import ktx.box2d.rayCast
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class ColissionListener: ContactListener {

    override fun beginContact(contact: Contact) {

        when (val type = contact.type()) {

            is ContactType.SpellWithEnemyOrWall -> type.spell.addCollision(type.enemyOrWall, contact.contactPoint())
            is ContactType.FormWithEnemyOrWall -> if (contact.hasLineOfSight()) type.form.addCollision(type.enemyOrWall, contact.contactPoint())
            is ContactType.EnemySensorToSteerable -> type.enemy.addNeighbor(type.neighbor)
            is ContactType.Unknown -> Unit
        }
    }

    override fun endContact(contact: Contact) {

        when (val type = contact.type()) {

            is ContactType.SpellWithEnemyOrWall -> Unit
            is ContactType.FormWithEnemyOrWall -> Unit
            is ContactType.EnemySensorToSteerable -> type.enemy.removeNeighbor(type.neighbor)
            is ContactType.Unknown -> Unit
        }
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {

       if (contact.fixtureA.filterData.categoryBits == SPELL || contact.fixtureB.filterData.categoryBits == SPELL)
            contact.isEnabled = false
    }

    override fun postSolve(contact: Contact, impulse: ContactImpulse) {}

    // CONTACT TYPES:
    //--------------------------------------------------------------------------------------------------------

    sealed interface ContactType {

        data class SpellWithEnemyOrWall(val spell: Spell, val enemyOrWall: Steerable): ContactType
        data class FormWithEnemyOrWall(val form: Form, val enemyOrWall: Steerable): ContactType
        data class EnemySensorToSteerable(val enemy: Enemy, val neighbor: Steerable): ContactType
        data object Unknown: ContactType
    }

    private fun Contact.type(): ContactType =

        withClassesAndBits(Spell::class, Steerable::class, SPELL) { a, b -> ContactType.SpellWithEnemyOrWall(a, b) }
            .flatMap { withClassesAndBits(Form:: class, Steerable::class, SPELL) { a, b -> ContactType.FormWithEnemyOrWall(a, b) } }
            .flatMap { withClassesAndBits(Enemy::class, Steerable::class, ENEMY_SENSOR) { a, b -> ContactType.EnemySensorToSteerable(a, b) } }
            .fold( ifLeft = { it }, ifRight = { ContactType.Unknown } )

    private fun <T: Any, D: Any> Contact.withClassesAndBits(classzOne: KClass<T>, classzTwo: KClass<D>, categoryBits: Short,
        toContactType: (userDataA: T, userDatab: D) -> ContactType): Either<ContactType, Contact> {

        val userDataA = fixtureA.body.userData
        val userDataB = fixtureB.body.userData

        return when {

            classzOne.isInstance(userDataA) && classzTwo.isInstance(userDataB) && fixtureA.filterData.categoryBits == categoryBits ->
                toContactType(userDataA as T, userDataB as D).left()

            classzOne.isInstance(userDataB) && classzTwo.isInstance(userDataA) && fixtureB.filterData.categoryBits == categoryBits ->
                toContactType(userDataB as T, userDataA as D).left()

            else -> this.right()
        }
    }

    // LINE OF SIGHT:
    //--------------------------------------------------------------------------------------------------------

    private fun Contact.hasLineOfSight(): Boolean {

        val positionA = this.fixtureA.body.position
        val positionB = this.fixtureB.body.position

        if (positionA.equals(positionB))
            return true

        var hasLos = true

        this.fixtureA.body.world.rayCast(positionA, positionB) { fixture, _, _, _ ->

            when(fixture.filterData.categoryBits == WALLS) {

                true -> { hasLos = false; RayCast.TERMINATE }
                false -> RayCast.CONTINUE
            }
        }
        return hasLos
    }

    private fun Contact.contactPoint(): PositionMeters =

        this.worldManifold.points.first().let { PositionMeters(it.x, it.y) }
}
