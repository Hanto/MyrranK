package com.myrran.domain.mob.metricunits

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

interface PositionI<T: Distance> {
    val x: T
    val y: T
}

data class PositionPixels(

    override val x: Pixel,
    override val y: Pixel,

): PositionI<Pixel>
{
    constructor(x: Int, y: Int): this(Pixel(x), Pixel(y))
    constructor(x: Float, y: Float): this(Pixel(x), Pixel(y))

    fun toVector(): Vector2 =

        Vector2(x.toFloat(), y.toFloat())

    fun toMeters(): PositionMeters =

        PositionMeters(x.toMeters(), y.toMeters())

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

    fun toVector(): Vector2 =

        Vector2(x.toFloat(), y.toFloat())

    fun toPixels(): PositionPixels =

        PositionPixels(x.toPixel(), y.toPixel())

    fun toScreenPosition(camera: OrthographicCamera): PositionPixels {

        val screenCoordinates = camera.project(Vector3(x.toFloat(), y.toFloat(), 0f))
        return PositionPixels(screenCoordinates.x, screenCoordinates.y)
    }
}

fun Vector2.toPositionPixels(): PositionPixels =

    PositionPixels(x, y)

fun Vector2.toPositionMeters(): PositionMeters =

    PositionMeters(x, y)
