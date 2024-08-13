package com.myrran.domain.mobs.steerable

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.myrran.domain.mobs.spells.spell.WorldBox2D
import com.myrran.domain.mobs.steerable.metrics.Distance
import com.myrran.domain.mobs.steerable.metrics.Size

class BodyFactory
{
    fun createSquareBody(world: WorldBox2D, size: Size<*>, ): Body {

        val sizeInMeters = size.toBox2dUnits()

        val bd = BodyDef()
        bd.type = BodyDef.BodyType.KinematicBody
        bd.position.set(0f, 0f)
        bd.fixedRotation = true

        val shape = PolygonShape()

        sizeInMeters.scl(0.5f)
        shape.setAsBox(sizeInMeters.x, sizeInMeters.y, sizeInMeters, 0f)

        val fixDef = FixtureDef()
        fixDef.shape = shape
        //fixDef.density = 60f

        val body = world.createBody(bd)
        body.createFixture(fixDef)
        //body.gravityScale = 0f
        //body.linearDamping = 0.3f
        //body.isSleepingAllowed = false

        shape.dispose()
        return body
    }

    fun createCircleBody(world: WorldBox2D, radius: Distance): Body {

        val bd = BodyDef()
        bd.type = BodyDef.BodyType.KinematicBody
        bd.position.set(0f, 0f)
        bd.fixedRotation = true

        val shape = CircleShape()
        shape.radius = radius.toBox2DUnits()

        val fixDef = FixtureDef()
        fixDef.shape = shape

        val body = world.createBody(bd)
        body.createFixture(fixDef)

        shape.dispose()
        return body
    }
}
