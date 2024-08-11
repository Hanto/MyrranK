package com.myrran.domain.mob

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.myrran.domain.mob.metrics.Pixel
import com.myrran.domain.mob.metrics.SizePixels

class BodyFactory(

    private val world: World
)
{
    fun createSquareBody(sizeInPixels: SizePixels): Body {

        val sizeInMeters = sizeInPixels.toMeters()

        val bd = BodyDef()
        bd.type = BodyDef.BodyType.KinematicBody
        bd.position.set(0f, 0f)
        bd.fixedRotation = true

        val shape = PolygonShape()

        val center = sizeInMeters.toVector().scl(0.5f, 0.5f)
        shape.setAsBox(sizeInMeters.width.toFloat() /2, sizeInMeters.height.toFloat() /2,
            center, 0f
        )

        val fixDef = FixtureDef()
        fixDef.shape = shape

        val body = world.createBody(bd)
        body.createFixture(fixDef)

        shape.dispose()
        return body
    }

    fun createCircleBody(radius: Pixel): Body {

        val bd = BodyDef()
        bd.type = BodyDef.BodyType.KinematicBody
        bd.position.set(0f, 0f)
        bd.fixedRotation = true

        val shape = CircleShape()
        shape.radius = radius.toMeters().toFloat()

        val fixDef = FixtureDef()
        fixDef.shape = shape

        val body = world.createBody(bd)
        body.createFixture(fixDef)

        shape.dispose()
        return body
    }
}
