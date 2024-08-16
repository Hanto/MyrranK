package com.myrran.domain.mobs.common

import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.physics.box2d.ContactImpulse
import com.badlogic.gdx.physics.box2d.ContactListener
import com.badlogic.gdx.physics.box2d.Fixture
import com.badlogic.gdx.physics.box2d.Manifold
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.ENEMY_SENSOR
import com.myrran.domain.mobs.mob.Enemy

class ColissionListener: ContactListener {

    override fun beginContact(contact: Contact) {

        doIf(contact, { it.filterData.categoryBits == ENEMY_SENSOR }, { a: Enemy, b: Mob -> a.enemiesNear.add(b) })
    }

    override fun endContact(contact: Contact) {

        doIf(contact, { it.filterData.categoryBits == ENEMY_SENSOR }, { a: Enemy, b: Mob -> a.enemiesNear.remove(b) })
    }

    private fun <T: Any, D: Any>doIf(contact: Contact, condition: (Fixture) -> Boolean, doThis: (T, D) -> Unit ) {

        if (condition.invoke(contact.fixtureA))
            doThis.invoke(contact.fixtureA.body.userData as T, contact.fixtureB.body.userData as D)

        else if (condition.invoke(contact.fixtureB))
            doThis.invoke(contact.fixtureB.body.userData as T, contact.fixtureA.body.userData as D)
    }

    override fun preSolve(contact: Contact, oldManifold: Manifold) {}
    override fun postSolve(contact: Contact, impulse: ContactImpulse) {}
}
