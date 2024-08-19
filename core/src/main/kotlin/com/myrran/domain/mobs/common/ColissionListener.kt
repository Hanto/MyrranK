package com.myrran.domain.mobs.common

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Manifold
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.mobs.common.ColissionListener.ContactType.EnemySensorToSteerable
import com.myrran.domain.mobs.common.ColissionListener.ContactType.FormWithMob
import com.myrran.domain.mobs.common.ColissionListener.ContactType.SpellWithEnemyOrWall
import com.myrran.domain.mobs.common.ColissionListener.ContactType.Unknown
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.ENEMY_SENSOR
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.SPELL
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.WALLS
import com.myrran.domain.mobs.common.corporeal.Corporeal
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.mob.Enemy
import com.myrran.domain.mobs.spells.form.Form
import com.myrran.domain.mobs.spells.spell.Spell
import ktx.box2d.RayCast
import ktx.box2d.rayCast


class ColissionListener: ContactListener {

    override fun beginContact(contact: Contact) {

        when (val type = contact.toContactType()) {

            is SpellWithEnemyOrWall -> type.spell.addCollision(type.enemyOrWall, contact.contactPoint())
            is FormWithMob -> type.form.addCollision(type.enemy, contact.contactPoint())
            is EnemySensorToSteerable -> type.enemy.addNeighbor(type.neighbor)
            is Unknown -> Unit
        }
    }

    override fun endContact(contact: Contact) {

        when (val type = contact.toContactType()) {

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

    private fun Contact.toContactType(): ContactType =

        this.are(Spell::class, Corporeal::class)
            .and { it.filterData.categoryBits == SPELL }.then { a, b -> SpellWithEnemyOrWall(a, b) } ?:

        this.are(Form::class, Mob::class)
            .and { it.filterData.categoryBits == SPELL }
            .andB { this.hasLineOfSightWith(it) }.then { a, b -> FormWithMob(a, b) } ?:

        this.are(Enemy::class, Mob::class)
            .and { it.filterData.categoryBits ==  ENEMY_SENSOR }.then { a, b -> EnemySensorToSteerable(a, b) } ?:

        Unknown

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

    // CONTACT TYPES:
    //--------------------------------------------------------------------------------------------------------

    sealed interface ContactType {

        data class SpellWithEnemyOrWall(val spell: Spell, val enemyOrWall: Corporeal): ContactType
        data class FormWithMob(val form: Form, val enemy: Mob): ContactType
        data class EnemySensorToSteerable(val enemy: Enemy, val neighbor: Steerable): ContactType
        data object Unknown: ContactType
    }
}
