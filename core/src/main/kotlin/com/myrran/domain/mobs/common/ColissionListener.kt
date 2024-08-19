package com.myrran.domain.mobs.common

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.myrran.domain.mobs.common.ColissionListener.ContactType.EnemySensorToSteerable
import com.myrran.domain.mobs.common.ColissionListener.ContactType.FormWithMob
import com.myrran.domain.mobs.common.ColissionListener.ContactType.SpellWithEnemyOrWall
import com.myrran.domain.mobs.common.ColissionListener.ContactType.Unknown
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.ENEMY_SENSOR
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.SPELL
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.WALLS
import com.myrran.domain.mobs.common.corporeal.Corporeal
import com.myrran.domain.mobs.common.metrics.PositionMeters
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

            is SpellWithEnemyOrWall -> type.spell.addCollision(type.enemyOrWall, contact.contactPoint())
            is FormWithMob -> if (contact.hasLineOfSightWith(type.enemy)) type.form.addCollision(type.enemy, contact.contactPoint())
            is EnemySensorToSteerable -> type.enemy.addNeighbor(type.neighbor)
            is Unknown -> Unit
        }
    }

    override fun endContact(contact: Contact) {

        when (val type = contact.type()) {

            is SpellWithEnemyOrWall -> Unit
            is FormWithMob -> Unit
            is EnemySensorToSteerable -> type.enemy.removeNeighbor(type.neighbor)
            is Unknown -> Unit
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

        data class SpellWithEnemyOrWall(val spell: Spell, val enemyOrWall: Corporeal): ContactType
        data class FormWithMob(val form: Form, val enemy: Mob): ContactType
        data class EnemySensorToSteerable(val enemy: Enemy, val neighbor: Steerable): ContactType
        data object Unknown: ContactType
    }

    private fun Contact.type(): ContactType =

        withClassesAndBits(Spell::class, Corporeal::class, SPELL) { a, b -> SpellWithEnemyOrWall(a, b) }
            .flatMap { withClassesAndBits(Form:: class, Mob::class, SPELL) { a, b -> FormWithMob(a, b) } }
            .flatMap { withClassesAndBits(Enemy::class, Steerable::class, ENEMY_SENSOR) { a, b -> EnemySensorToSteerable(a, b) } }
            .fold( ifLeft = { it }, ifRight = { Unknown } )


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

    private fun Contact.hasLineOfSightWith(corporeal: Corporeal): Boolean {

        if (corporeal.position == contactPoint().toBox2dUnits())
            return true

        var hasLos = true

        this.fixtureA.body.world.rayCast(corporeal.position, contactPoint().toBox2dUnits()) { fixture, _, _, _ ->

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
