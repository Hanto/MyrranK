package com.myrran.domain.mob

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.myrran.domain.mob.metrics.SizePixels

class BodyFactory(

    private val world: World
)
{
    fun createSquareBody(size: SizePixels): Body {

        val bd = BodyDef()
        bd.type = BodyDef.BodyType.KinematicBody
        bd.position.set(0f, 0f)
        bd.fixedRotation = true

        val shape = PolygonShape()
        val center = Vector2(size.width.toMeters().toFloat()/2, size.height.toMeters().toFloat()/2)

        shape.radius = size.width.toMeters().toFloat() /2
        //bd.position.set(center)
        shape.setAsBox(
            size.width.toMeters().toFloat() /2,
            size.height.toMeters().toFloat() /2,
                center, 0f
        )

        val fixDef = FixtureDef()
        fixDef.shape = shape

        val body = world.createBody(bd)
        body.createFixture(fixDef)

        shape.dispose()
        return body
    }
}
