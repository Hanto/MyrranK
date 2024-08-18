package com.myrran.domain.mobs.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.myrran.domain.mobs.common.ColissionListener.ContactType.EnemySensorToSteerable
import com.myrran.domain.mobs.common.ColissionListener.ContactType.SpellFormWithEnemyOrWall
import com.myrran.domain.mobs.common.ColissionListener.ContactType.Unknown
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.ENEMY_SENSOR
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.SPELL
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.mob.Enemy
import com.myrran.domain.mobs.spells.spell.Spell
import kotlin.reflect.KClass

class ColissionListener: ContactListener {

    override fun beginContact(contact: Contact) {

        when (val type = contact.type()) {

            is SpellFormWithEnemyOrWall -> type.spell.addCollision(type.enemyOrWall)
            is EnemySensorToSteerable -> type.enemy.addNeighbor(type.neighbor)
            is Unknown -> Unit
        }
    }

    override fun endContact(contact: Contact) {

        when (val type = contact.type()) {

            is SpellFormWithEnemyOrWall -> Unit
            is EnemySensorToSteerable -> type.enemy.removeNeighbor(type.neighbor)
            is Unknown -> Unit
        }
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {}
    override fun postSolve(contact: Contact, impulse: ContactImpulse) {}

    // CONTACT TYPES:
    //--------------------------------------------------------------------------------------------------------

    private fun Contact.type(): ContactType =

        withClassesAndBits(Spell::class, Steerable::class, SPELL) { a, b -> SpellFormWithEnemyOrWall(a, b) }
            .mapLeft { withClassesAndBits(Enemy::class, Steerable::class, ENEMY_SENSOR) { a, b -> EnemySensorToSteerable(a, b) } }
            .fold( { left -> left.fold( { Unknown }, { it }) }, { right -> right })

    private fun <T: Any, D: Any> Contact.withClassesAndBits(classzOne: KClass<T>, classzTwo: KClass<D>, categoryBits: Short, toContactType: (userDataA: T, userDatab: D) -> ContactType): Either<Contact, ContactType> =

        if (classzOne.isInstance(fixtureA.body.userData) && classzTwo.isInstance(fixtureB.body.userData) && fixtureA.filterData.categoryBits == categoryBits)
            toContactType(fixtureA.body.userData as T, fixtureB.body.userData as D).right()
        else if (classzOne.isInstance(fixtureB.body.userData) && classzTwo.isInstance(fixtureA.body.userData) && fixtureB.filterData.categoryBits == categoryBits)
            toContactType(fixtureB.body.userData as T, fixtureA.body.userData as D).right()
        else this.left()

    private sealed interface ContactType {

        data class SpellFormWithEnemyOrWall(val spell: Spell, val enemyOrWall: Steerable): ContactType
        data class EnemySensorToSteerable(val enemy: Enemy, val neighbor: Steerable): ContactType
        data object Unknown: ContactType
    }
}
