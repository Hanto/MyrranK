package com.myrran.domain.mobs.common.steerable

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.myrran.domain.mobs.common.metrics.Distance
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.BULLET
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.ENEMY
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.ENEMY_SENSOR
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.LIGHT_PLAYER
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.PLAYER
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.WALLS
import com.myrran.domain.mobs.spells.spell.WorldBox2D
import kotlin.experimental.or

class BodyFactory
{
    fun createPlayerBody(world: WorldBox2D, radius: Distance): Body {

        val bd = BodyDef()
            .also { it.type = BodyDef.BodyType.DynamicBody }
            .also { it.fixedRotation = false }

        val shape = CircleShape()
            .also { it.radius = radius.toBox2DUnits()}

        val fixDef = FixtureDef()
            .also { it.shape = shape }
            .also { it.filter.categoryBits = PLAYER }
            .also { it.filter.maskBits = BULLET or LIGHT_PLAYER or ENEMY_SENSOR or ENEMY }
            .also { it.density = 100f }

        val body = world.createBody(bd)
            .also { it.createFixture(fixDef) }

        shape.dispose()
        return body
    }

    fun createEnemyBody(world: WorldBox2D, radius: Distance): Body {

        val bd = BodyDef()
            .also { it.type = BodyDef.BodyType.DynamicBody }
            .also { it.fixedRotation = false }

        val shape = CircleShape()
            .also { it.radius = radius.toBox2DUnits() }

        val fixDef = FixtureDef()
            .also { it.shape = shape }
            .also { it.filter.categoryBits = ENEMY }
            .also { it.filter.maskBits = PLAYER or ENEMY or BULLET or LIGHT_PLAYER or ENEMY_SENSOR }
            .also { it.density = 100f }

        val shapeSensor = CircleShape()
            .also { it.radius = Pixel(50).toBox2DUnits() }

        val fixDefSensor = FixtureDef()
            .also { it.shape = shapeSensor }
            .also { it.filter.categoryBits = ENEMY_SENSOR }
            .also { it.filter.maskBits = ENEMY or PLAYER }
            .also { it.isSensor = true }

        val body = world.createBody(bd)
            .also { it.createFixture(fixDef) }
            .also { it.createFixture(fixDefSensor) }

        shape.dispose()
        shapeSensor.dispose()
        return body
    }

    fun createSpellBoltBody(world: WorldBox2D, radius: Distance): Body {

        val bd = BodyDef()
            .also { it.type = BodyDef.BodyType.KinematicBody }

        val shape = CircleShape()
            .also { it.radius = radius.toBox2DUnits() }

        val fixDef = FixtureDef()
            .also { it.shape = shape }
            .also { it.filter.categoryBits = BULLET }
            .also { it.filter.maskBits = ENEMY or WALLS }

        val body = world.createBody(bd)
            .also { it.createFixture(fixDef) }

        shape.dispose()
        return body
    }
}
