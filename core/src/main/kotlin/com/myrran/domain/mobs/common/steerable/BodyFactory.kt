package com.myrran.domain.mobs.common.steerable

import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.myrran.domain.mobs.common.metrics.Distance
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.common.metrics.Size
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.BODY
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.BULLET
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.LIGHT
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.PLAYER
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.WALLS
import com.myrran.domain.mobs.spells.spell.WorldBox2D
import kotlin.experimental.or

class BodyFactory
{
    fun createPlayerBody(world: WorldBox2D, size: Size<*>, ): Body {

        val bd = BodyDef()
            .also { it.type = BodyDef.BodyType.KinematicBody }
            .also { it.fixedRotation = false }

        /*val dimensions = size.toBox2dUnits().scl(0.5f)
        val shape = PolygonShape()
            .also { it.setAsBox(dimensions.x, dimensions.y, dimensions, 0f) } */

        val shape = CircleShape()
            .also { it.radius = Pixel(16).toBox2DUnits()}

        val fixDef = FixtureDef()
            .also { it.shape = shape }
            .also { it.filter.categoryBits = PLAYER }
            .also { it.filter.maskBits = BODY or BULLET or LIGHT }

        val body = world.createBody(bd)
            .also { it.createFixture(fixDef) }

        shape.dispose()
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
            .also { it.filter.maskBits = BODY or WALLS }

        val body = world.createBody(bd)
            .also { it.createFixture(fixDef) }

        shape.dispose()
        return body
    }
}
