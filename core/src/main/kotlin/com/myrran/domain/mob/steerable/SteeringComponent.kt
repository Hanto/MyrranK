package com.myrran.domain.mob.steerable

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.ai.steer.Limiter
import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.ai.steer.SteeringAcceleration
import com.badlogic.gdx.ai.steer.SteeringBehavior
import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool.Poolable
import com.myrran.domain.mob.Movable

class SteeringComponent(

    override val location: Spatial,
    private val speedLimits: SpeedLimits,

    private var isTagged: Boolean = false,
    private var isFacingAutomatic: Boolean = false

): Steerable<Vector2>, Location<Vector2> by location, Limiter by speedLimits, Movable, Component, Poolable
{
    private var steeringOutput: SteeringAcceleration<Vector2> = SteeringAcceleration(Vector2())
    private var steeringBehavior: SteeringBehavior<Vector2>? = null
    private var lastPosition: Vector2 = Vector2(0f, 0f)

    override fun getLinearVelocity(): Vector2 =

        location.linearVelocity()

    override fun getAngularVelocity(): Float =

        location.angularVelocity()

    override fun getBoundingRadius(): Float =

        1f

    override fun isTagged(): Boolean =

        isTagged

    override fun setTagged(tagged: Boolean) {

        isTagged = tagged
    }

    override fun reset() {

    }

    override fun setLinearVelocity(direction: Vector2, value: Float) =

        location.setLinearVelocity(direction, value)

    override fun setPosition(position: Vector2) =

        location.setPosition(position)

    override fun saveLastPosition() {

        lastPosition = position.cpy() }

    override fun getLastPosition(): Vector2 =

        lastPosition

    // STEERING:
    //--------------------------------------------------------------------------------------------------------

    fun update(deltaTime: Float) {

        steeringBehavior?.calculateSteering(steeringOutput)
            ?.let { applySteering(it, deltaTime) }
    }

    private fun applySteering(steering: SteeringAcceleration<Vector2>, deltaTime: Float) {

        if (!steering.linear.isZero(speedLimits.zeroLinearSpeedThreshold)) {

            location.applyForceToCenter(steering.linear) // Box2D internally scales the force by deltaTime
        }

        when (isFacingAutomatic) {

            true -> orientationBasedOnSteering(steering)
            false -> orientationBasedOnCurrentDirection()
        }
    }

    private fun orientationBasedOnSteering(steering: SteeringAcceleration<Vector2>) {

        if (steering.angular != 0f) {

            location.applyTorque(steeringOutput.angular) // Box2D internally scales the force by deltaTime
        }
    }

    private fun orientationBasedOnCurrentDirection() {

        if (!location.linearVelocity().isZero(speedLimits.zeroLinearSpeedThreshold)) {

            val newOrientation = location.vectorToAngle(location.linearVelocity())
            location.orientation = newOrientation
        }
    }
}
