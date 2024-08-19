package com.myrran.domain.mobs.common.corporeal

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.myrran.domain.misc.constants.WorldBox2D
import com.myrran.domain.misc.metrics.Degree
import com.myrran.domain.misc.metrics.Distance
import com.myrran.domain.misc.metrics.Pixel
import com.myrran.domain.misc.metrics.Size
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.ENEMY
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.ENEMY_LOS
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.ENEMY_SENSOR
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.LIGHT_PLAYER
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.PLAYER
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.SPELL
import com.myrran.domain.mobs.common.corporeal.Box2dFilters.Companion.WALLS
import ktx.box2d.body
import ktx.box2d.box
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
                filter.maskBits = SPELL or LIGHT_PLAYER or ENEMY_SENSOR or ENEMY or ENEMY_LOS or WALLS
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
                filter.maskBits = PLAYER or ENEMY or SPELL or LIGHT_PLAYER or ENEMY_SENSOR or WALLS
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
                filter.maskBits = PLAYER
                isSensor = true
            }
    }

    fun createSpellBoltBody(world: WorldBox2D, radius: Distance): Body =

        world.body {

            type = BodyDef.BodyType.DynamicBody
            fixedRotation = false
            bullet = true

            circle {

                it.radius = radius.toBox2DUnits()
                filter.categoryBits = SPELL
                filter.maskBits = ENEMY or WALLS
                isSensor = false
            }
        }

    fun createCircleForm(world: WorldBox2D, radius: Distance): Body =

        world.body {

            type = BodyDef.BodyType.KinematicBody
            fixedRotation = false

            circle {

                it.radius = radius.toBox2DUnits()
                filter.categoryBits = SPELL
                filter.maskBits = ENEMY or PLAYER
                isSensor = true
            }
        }

    fun createWall(world: WorldBox2D, size: Size<*>) =

        world.body {

            type = BodyDef.BodyType.StaticBody
            fixedRotation = true

            box( size.width.toBox2DUnits(), size.height.toBox2DUnits()) {

                filter.categoryBits = WALLS
                filter.maskBits = ENEMY or SPELL or PLAYER
                restitution = 1f
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
