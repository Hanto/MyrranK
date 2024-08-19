package com.myrran.domain.mobs.common.corporeal

import com.badlogic.gdx.ai.steer.Limiter
import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.utils.Disposable
import ktx.math.minus

data class CorporealComponent(

    val body: Body,
    private val limiter: Limiter,
    private val destroyFunction: () -> Unit

): Corporeal, Spatial, Movable, Limiter by limiter, Disposable
{
    private var lastPosition: Vector2 = Vector2(0f, 0f)

    // POSITION: (Spatial)
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

        DumbLocation()

    override fun setPosition(position: Vector2) =

        body.setTransform(position, body.angle)

    override fun saveLastPosition() {

        lastPosition = position.cpy() }

    override fun getInterpolatedPosition(fractionOfTimestep: Float): Vector2 {

        val offset = position.minus(lastPosition)

        return Vector2(
        lastPosition.x + offset.x * fractionOfTimestep,
        lastPosition.y + offset.y * fractionOfTimestep )
    }

    // VELOCITY: (Movible)
    //--------------------------------------------------------------------------------------------------------

    override fun applyImpulse(direction: Vector2, value: Float) {

        body.applyLinearImpulse(direction.nor().scl(value), position, true)
        body.linearVelocity = body.linearVelocity.limit(limiter.maxLinearSpeed)
        orientation = direction.angleRad()
    }

    override fun setLinearVelocity(direction: Vector2, value: Float) {

        body.linearVelocity = direction.nor().scl(value).limit(limiter.maxLinearSpeed)
        orientation = direction.angleRad()
    }

    override fun getLinearVelocity(): Vector2 =

        body.linearVelocity

    override fun getAngularVelocity(): Float =

        body.angularVelocity

    override fun applyForceToCenter(force: Vector2) {

        body.applyForceToCenter(force, true)
        body.linearVelocity = body.linearVelocity.limit(limiter.maxLinearSpeed)
    }

    override fun applyTorque(angular: Float) {

        body.applyTorque(angular, true)
        body.angularVelocity = body.angularVelocity.coerceAtMost(limiter.maxAngularSpeed)
    }

    // DISPOSABLE:
    //--------------------------------------------------------------------------------------------------------

    override fun dispose() =

        destroyFunction.invoke()
}
