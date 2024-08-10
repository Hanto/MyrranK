package com.myrran.domain.mob

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.ai.steer.Limiter
import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.ai.steer.SteeringAcceleration
import com.badlogic.gdx.ai.steer.SteeringBehavior
import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool.Poolable

class SteeringComponent(

    private val spatial: Spatial,
    private val speedLimits: SpeedLimits,

    private var isTagged: Boolean = false,
    private var isFacingAutomatic: Boolean = false

): Steerable<Vector2>, Component, Poolable, Location<Vector2> by spatial, Limiter by speedLimits
{
    private var steeringOutput: SteeringAcceleration<Vector2> = SteeringAcceleration(Vector2())
    private var steeringBehavior: SteeringBehavior<Vector2>? = null

    override fun getLinearVelocity(): Vector2 =

        spatial.linearVelocity()

    override fun getAngularVelocity(): Float =

        spatial.angularVelocity()

    override fun getBoundingRadius(): Float =

        1f

    override fun isTagged(): Boolean =

        isTagged

    override fun setTagged(tagged: Boolean) {

        isTagged = tagged
    }

    override fun reset() {

    }

    // STEERING:
    //--------------------------------------------------------------------------------------------------------

    fun update(deltaTime: Float) {

        steeringBehavior?.calculateSteering(steeringOutput)
            ?.let { applySteering(it, deltaTime) }
    }

    private fun applySteering(steering: SteeringAcceleration<Vector2>, deltaTime: Float) {

        if (!steering.linear.isZero(speedLimits.zeroLinearSpeedThreshold)) {

            spatial.applyForceToCenter(steering.linear, speedLimits) // Box2D internally scales the force by deltaTime
        }

        when (isFacingAutomatic) {

            true -> orientationBasedOnSteering(steering)
            false -> orientationBasedOnCurrentDirection()
        }
    }

    private fun orientationBasedOnSteering(steering: SteeringAcceleration<Vector2>) {

        if (steering.angular != 0f) {

            spatial.applyTorque(steeringOutput.angular, speedLimits) // Box2D internally scales the force by deltaTime
        }
    }

    private fun orientationBasedOnCurrentDirection() {

        if (!spatial.linearVelocity().isZero(speedLimits.zeroLinearSpeedThreshold)) {

            val newOrientation = spatial.vectorToAngle(spatial.linearVelocity())
            spatial.orientation = newOrientation
        }
    }
}
