package com.myrran.domain.mob.metricunits

import com.badlogic.gdx.math.Vector2

interface Size<T: Distance> {

    val width: T
    val height: T
}

data class SizePixels(

    override val width: Pixel,
    override val height: Pixel,

): Size<Pixel> {

    constructor(x: Int, y: Int): this(Pixel(x), Pixel(y))
    constructor(x: Float, y: Float): this(Pixel(x), Pixel(y))


    fun toVector(): Vector2 =

        Vector2(width.toFloat(), height.toFloat())

    fun toMeters(): SizeMeters =

        SizeMeters(width.toMeters(), height.toMeters())
}

data class SizeMeters(

    override val width: Meter,
    override val height: Meter,

): Size<Meter> {

    constructor(x: Float, y: Float): this(Meter(x), Meter(y))

    fun toVector(): Vector2 =

        Vector2(width.toFloat(), height.toFloat())

    fun toPixels(): SizePixels =

        SizePixels(width.toPixel(), height.toPixel())
}
