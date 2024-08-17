package com.myrran.domain.mobs.common.steerable

import box2dLight.PointLight
import com.badlogic.gdx.ai.steer.Limiter
import com.badlogic.gdx.ai.steer.SteeringAcceleration
import com.badlogic.gdx.ai.steer.SteeringBehavior
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.mobs.common.metrics.Pixel

class SteerableByBox2DComponent(

    private val movable: MovableByBox2D,
    private val speedLimiter: SpeedLimiter,
    private var isFacingAutomatic: Boolean = false

): Steerable, Movable by movable, Limiter by speedLimiter, Disposable
{
    var steeringBehavior: SteeringBehavior<Vector2>? = null
    private var steeringOutput: SteeringAcceleration<Vector2> = SteeringAcceleration(Vector2())
    private var isTagged: Boolean = false

    override fun getBoundingRadius(): Float =

        Pixel(20).toBox2DUnits()

    override fun isTagged(): Boolean =

        isTagged

    override fun setTagged(tagged: Boolean) {

        isTagged = tagged
    }

    fun attachLight(light: PointLight) =

        light.attachToBody(movable.body)

    // DISPOSABLE:
    //--------------------------------------------------------------------------------------------------------

    override fun dispose() =

        movable.dispose()

    // STEERING:
    //--------------------------------------------------------------------------------------------------------

    fun update(deltaTime: Float) {

        steeringBehavior?.calculateSteering(steeringOutput)
            ?.let { applySteering(it, deltaTime) }
    }

    private fun applySteering(steering: SteeringAcceleration<Vector2>, deltaTime: Float) {

        if (!steering.linear.isZero(speedLimiter.zeroLinearSpeedThreshold)) {

            movable.applyForceToCenter(steering.linear) // Box2D internally scales the force by deltaTime
        }

        when (isFacingAutomatic) {

            true -> orientationBasedOnSteering(steering)
            false -> orientationBasedOnCurrentDirection()
        }
    }

    private fun orientationBasedOnSteering(steering: SteeringAcceleration<Vector2>) {

        if (steering.angular != 0f) {

            movable.applyTorque(steeringOutput.angular) // Box2D internally scales the force by deltaTime
        }
    }

    private fun orientationBasedOnCurrentDirection() {

        if (!movable.getLinearVelocity().isZero(speedLimiter.zeroLinearSpeedThreshold)) {

            val newOrientation = movable.vectorToAngle(movable.getLinearVelocity())
            movable.orientation = newOrientation
        }
    }
}
