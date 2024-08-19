package com.myrran.domain.mobs.common.steerable

import box2dLight.PointLight
import com.badlogic.gdx.ai.steer.SteeringAcceleration
import com.badlogic.gdx.ai.steer.SteeringBehavior
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.misc.metrics.Pixel
import com.myrran.domain.mobs.common.corporeal.Corporeal
import com.myrran.domain.mobs.common.corporeal.CorporealComponent

class SteerableComponent(

    private val corporeal: CorporealComponent,
    private var isFacingAutomatic: Boolean = false

): Steerable, Corporeal by corporeal, Disposable
{
    var steeringBehavior: SteeringBehavior<Vector2>? = null
    private var steeringOutput: SteeringAcceleration<Vector2> = SteeringAcceleration(Vector2())
    private var isTagged: Boolean = false

    // STEERABLE:
    //--------------------------------------------------------------------------------------------------------

    override fun getBoundingRadius(): Float =

        Pixel(20).toBox2DUnits()

    override fun isTagged(): Boolean =

        isTagged

    override fun setTagged(tagged: Boolean) {

        isTagged = tagged
    }

    fun attachLight(light: PointLight) =

        light.attachToBody(corporeal.body)

    // DISPOSABLE:
    //--------------------------------------------------------------------------------------------------------

    override fun dispose() =

        corporeal.dispose()

    // STEERING:
    //--------------------------------------------------------------------------------------------------------

    fun update(deltaTime: Float) {

        steeringBehavior?.calculateSteering(steeringOutput)
            ?.let { applySteering(it, deltaTime) }
    }

    private fun applySteering(steering: SteeringAcceleration<Vector2>, deltaTime: Float) {

        if (!steering.linear.isZero(corporeal.zeroLinearSpeedThreshold)) {

            corporeal.applyForceToCenter(steering.linear) // Box2D internally scales the force by deltaTime
        }

        when (isFacingAutomatic) {

            true -> orientationBasedOnSteering(steering)
            false -> orientationBasedOnCurrentDirection()
        }
    }

    private fun orientationBasedOnSteering(steering: SteeringAcceleration<Vector2>) {

        if (steering.angular != 0f) {

            corporeal.applyTorque(steeringOutput.angular) // Box2D internally scales the force by deltaTime
        }
    }

    private fun orientationBasedOnCurrentDirection() {

        if (!corporeal.getLinearVelocity().isZero(corporeal.zeroLinearSpeedThreshold)) {

            val newOrientation = corporeal.vectorToAngle(corporeal.getLinearVelocity())
            corporeal.orientation = newOrientation
        }
    }
}
