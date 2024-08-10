package com.myrran.domain.mob

import com.badlogic.gdx.ai.steer.Limiter
import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body

data class Spatial(

    private val body: Body,

): Location<Vector2>
{
    // POSITION:
    //--------------------------------------------------------------------------------------------------------

    override fun getPosition(): Vector2 =

        body.position

    override fun getOrientation(): Float =

        body.angle

    override fun setOrientation(newOrientation: Float) =

        body.setTransform(body.position, newOrientation)

    override fun angleToVector(outVector: Vector2, angle: Float): Vector2 =

        outVector.setAngleRad(angle)

    override fun vectorToAngle(vector: Vector2): Float =

        vector.angleRad()

    override fun newLocation(): Location<Vector2> =

        DumbSpatial()

    // VELOCITY:
    //--------------------------------------------------------------------------------------------------------

    fun linearVelocity(): Vector2 =

        body.linearVelocity

    fun angularVelocity(): Float =

        body.angularVelocity

    fun applyForceToCenter(force: Vector2, limits: Limiter) {

        body.applyForceToCenter(force, true)
        body.linearVelocity.limit(limits.maxLinearSpeed)
    }

    fun applyTorque(angular: Float, limits: Limiter) {

        body.applyTorque(angular, true)
        body.angularVelocity = body.angularVelocity.coerceAtMost(limits.maxAngularSpeed)
    }

}
