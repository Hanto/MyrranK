package com.myrran.domain.mob.steerable

import com.badlogic.gdx.ai.steer.Limiter
import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.myrran.domain.mob.metrics.PositionMeters

data class Spatial(

    private val body: Body,
    private val limiter: Limiter

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

    fun setPosition(position: PositionMeters) =

        body.setTransform(position.toVector(), body.angle)

    // VELOCITY:
    //--------------------------------------------------------------------------------------------------------

    fun setLinearVelocity(direction: Vector2, value: Float) {

        body.linearVelocity = direction.nor().scl(value).limit(limiter.maxAngularSpeed) }

    fun linearVelocity(): Vector2 =

        body.linearVelocity

    fun angularVelocity(): Float =

        body.angularVelocity

    fun applyForceToCenter(force: Vector2) {

        body.applyForceToCenter(force, true)
        body.linearVelocity = body.linearVelocity.limit(limiter.maxLinearSpeed)
    }

    fun applyTorque(angular: Float) {

        body.applyTorque(angular, true)
        body.angularVelocity = body.angularVelocity.coerceAtMost(limiter.maxAngularSpeed)
    }

}
