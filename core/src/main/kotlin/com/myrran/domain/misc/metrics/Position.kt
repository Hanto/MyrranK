package com.myrran.domain.misc.metrics

import com.badlogic.gdx.math.Vector2

interface Position<T: Distance> {
    val x: T
    val y: T
    fun toBox2dUnits(): Vector2
}

data class PositionPixels(

    override val x: Pixel,
    override val y: Pixel,

): Position<Pixel>
{
    constructor(x: Int, y: Int): this(Pixel(x), Pixel(y))
    constructor(x: Float, y: Float): this(Pixel(x), Pixel(y))

    fun toMeters(): PositionMeters =

        PositionMeters(x.toMeters(), y.toMeters())

    override fun toBox2dUnits(): Vector2 =

        Vector2(x.toBox2DUnits(), y.toBox2DUnits())
}

data class PositionMeters(

    override val x: Meter,
    override val y: Meter

): Position<Meter>
{
    constructor(x: Float, y: Float): this(Meter(x), Meter(y))
    constructor(vector2: Vector2): this(Meter(vector2.x), Meter(vector2.y))

    fun toPixels(): PositionPixels =

        PositionPixels(x.toPixel(), y.toPixel())

    override fun toBox2dUnits(): Vector2 =

        Vector2(x.toBox2DUnits(), y.toBox2DUnits())
}
