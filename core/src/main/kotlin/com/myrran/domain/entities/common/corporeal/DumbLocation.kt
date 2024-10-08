package com.myrran.domain.entities.common.corporeal

import com.badlogic.gdx.ai.utils.Location
import com.badlogic.gdx.math.Vector2

data class DumbLocation(

    private var position: Vector2 = Vector2(0f,0f),
    private var orientation: Float = 0f

): Location<Vector2>
{
    override fun getPosition(): Vector2 =

        position

    override fun getOrientation(): Float =

        orientation

    override fun setOrientation(newOrientation: Float) {

        orientation = newOrientation
    }

    override fun angleToVector(outVector: Vector2, angle: Float): Vector2 =

        outVector.setAngleRad(angle)

    override fun vectorToAngle(vector: Vector2): Float =

        vector.angleRad()

    override fun newLocation(): Location<Vector2> =

        DumbLocation()
}
