package com.myrran.domain.mob

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.myrran.domain.mob.metrics.Distance
import com.myrran.domain.mob.metrics.Size

class BodyFactory(

    private val world: World
)
{
    fun createSquareBody(size: Size<*>): Body {

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

    fun createCircleBody(radius: Distance): Body {

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
