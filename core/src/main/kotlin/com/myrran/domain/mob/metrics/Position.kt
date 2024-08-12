package com.myrran.domain.mob.metrics

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

interface PositionI<T: Distance> {
    val x: T
    val y: T
    fun toBox2dUnits(): Vector2
}

data class PositionPixels(

    override val x: Pixel,
    override val y: Pixel,

): PositionI<Pixel>
{
    constructor(x: Int, y: Int): this(Pixel(x), Pixel(y))
    constructor(x: Float, y: Float): this(Pixel(x), Pixel(y))

    fun toMeters(): PositionMeters =

        PositionMeters(x.toMeters(), y.toMeters())

    override fun toBox2dUnits(): Vector2 =

        Vector2(x.toBox2DUnits(), y.toBox2DUnits())

    fun toWorldPosition(camera: OrthographicCamera): PositionMeters {

        val worldCoordinates = camera.unproject(Vector3(x.toFloat(), y.toFloat(), 0f))
        return PositionMeters(worldCoordinates.x, worldCoordinates.y)
    }
}

data class PositionMeters(

    override val x: Meter,
    override val y: Meter

): PositionI<Meter>
{
    constructor(x: Float, y: Float): this(Meter(x), Meter(y))

    fun toPixels(): PositionPixels =

        PositionPixels(x.toPixel(), y.toPixel())

    override fun toBox2dUnits(): Vector2 =

        Vector2(x.toBox2DUnits(), y.toBox2DUnits())

    fun toScreenPosition(camera: OrthographicCamera): PositionPixels {

        val screenCoordinates = camera.project(Vector3(x.toFloat(), y.toFloat(), 0f))
        return PositionPixels(screenCoordinates.x, screenCoordinates.y)
    }
}
