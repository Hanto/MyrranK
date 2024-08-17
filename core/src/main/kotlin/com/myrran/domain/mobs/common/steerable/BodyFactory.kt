package com.myrran.domain.mobs.common.steerable

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.myrran.domain.mobs.common.metrics.Degree
import com.myrran.domain.mobs.common.metrics.Distance
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.BULLET
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.ENEMY
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.ENEMY_LOS
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.ENEMY_SENSOR
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.LIGHT_PLAYER
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.PLAYER
import com.myrran.domain.mobs.common.steerable.Box2dFilters.Companion.WALLS
import com.myrran.domain.mobs.spells.spell.WorldBox2D
import ktx.box2d.body
import ktx.box2d.circle
import ktx.box2d.polygon
import kotlin.experimental.or
import kotlin.math.cos
import kotlin.math.sin

class BodyFactory
{
    fun createPlayerBody(world: WorldBox2D, radius: Distance): Body =

        world.body {

            type= BodyDef.BodyType.DynamicBody
            fixedRotation = true

            circle {

                it.radius = radius.toBox2DUnits()
                filter.categoryBits = PLAYER
                filter.maskBits = BULLET or LIGHT_PLAYER or ENEMY_SENSOR or ENEMY
                density = 100f
            }
        }

    fun createEnemyBody(world: WorldBox2D, radius: Distance): Body =

        world.body {

            type = BodyDef.BodyType.DynamicBody
            fixedRotation = false

            circle {

                it.radius = radius.toBox2DUnits()
                filter.categoryBits = ENEMY
                filter.maskBits = PLAYER or ENEMY or BULLET or LIGHT_PLAYER or ENEMY_SENSOR
                density = 100f
            }
            circle {

                it.radius = Pixel(60).toBox2DUnits()
                filter.categoryBits = ENEMY_SENSOR
                filter.maskBits = ENEMY or PLAYER
                isSensor = true
            }
            polygon {

                it.set(createConeVertices(Pixel(300), Degree(45f)))
                filter.categoryBits = ENEMY_LOS
                isSensor = true
            }
    }

    fun createSpellBoltBody(world: WorldBox2D, radius: Distance): Body =

        world.body {

            type = BodyDef.BodyType.KinematicBody
            fixedRotation = false

            circle {

                it.radius = radius.toBox2DUnits()
                filter.categoryBits = BULLET
                filter.maskBits = ENEMY or WALLS
            }
        }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createConeVertices(radius: Distance, angle: Degree): Array<Vector2> {

        val vertices = mutableListOf(Vector2(0f, 0f))

        for (i in 0..6) {

            val radians = (angle * i / 6 - angle / 2).toRadians().toFloat()
            val vector = Vector2(radius.toBox2DUnits() * cos(radians), radius.toBox2DUnits() * sin(radians))
            vertices.add(vector)
        }
        return vertices.toTypedArray()
    }
}
