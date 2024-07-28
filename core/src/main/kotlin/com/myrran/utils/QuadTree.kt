package com.myrran.utils

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class QuadNode<V> (

    private val NW: QuadTree<V>,
    private val NE: QuadTree<V>,
    private val SW: QuadTree<V>,
    private val SE: QuadTree<V>

) : QuadTree<V> {

    override val frame: Rect = Rect(NW.frame.TL, SE.frame.BR)

    constructor(frame: Rect) : this(
        NW = QuadNil<V>(Rect(frame.origin, frame.width / 2, frame.height / 2)),
        NE = QuadNil<V>(Rect(Point(frame.x1 + frame.width / 2 + 1, frame.y1), frame.width / 2, frame.height / 2)),
        SW = QuadNil<V>(Rect(Point(frame.x1, frame.y1 + frame.height / 2 + 1), frame.width / 2, frame.height / 2)),
        SE = QuadNil<V>(Rect(frame.center, frame.width / 2, frame.height / 2))
    )

    override fun get(rect: Rect): Iterable<V> =
        (if (NW.frame.intersects(rect)) NW[rect] else emptyList()) +
        (if (NE.frame.intersects(rect)) NE[rect] else emptyList()) +
        (if (SW.frame.intersects(rect)) SW[rect] else emptyList()) +
        (if (SE.frame.intersects(rect)) SE[rect] else emptyList())


    override fun plus(pair: Pair<Point, V>): QuadTree<V> = QuadNode(
        if (NW.frame.isInside(pair.first)) NW + pair else NW,
        if (NE.frame.isInside(pair.first)) NE + pair else NE,
        if (SW.frame.isInside(pair.first)) SW + pair else SW,
        if (SE.frame.isInside(pair.first)) SE + pair else SE
    )
}

class QuadLeaf<V>(

    override val frame: Rect,
    val value: Pair<Point, V>

): QuadTree<V> {

    override fun get(rect: Rect): Iterable<V> =

        if (rect.isInside(value.first)) listOf(value.second)
        else emptyList()

    override fun plus(pair: Pair<Point, V>): QuadTree<V> =

        QuadNode<V>(frame.cover(pair.first)) + value + pair
}

class QuadNil<V>(

    override val frame: Rect

) : QuadTree<V> {

    override fun get(rect: Rect): Iterable<V> = emptyList()

    override fun plus(pair: Pair<Point, V>): QuadLeaf<V> =

        QuadLeaf(frame.cover(pair.first), value = pair)
}

interface QuadTree<V> {

    val frame: Rect
    operator fun get(rect: Rect): Iterable<V>
    operator fun plus(pair: Pair<Point, V>): QuadTree<V>
}

fun<V> emptyQuadTree(frame: Rect): QuadTree<V> = QuadNil(frame)

fun<V> quadTreeOf(frame: Rect, vararg pairs: Pair<Point, V>): QuadTree<V> {

    var empty = emptyQuadTree<V>(frame)
    for (pair in pairs) {
        empty += pair
    }
    return empty
}

data class Rect(

    val x1: Int,
    val y1: Int,
    val x2: Int,
    val y2: Int

) {

    val width = x2 - x1
    val height = y2 - y1
    val origin = Point(x1, y1)
    val center = Point(origin.x + width / 2, origin.y + height / 2)
    val TL = origin
    val BR = Point(origin.x + width, origin.y + height)

    constructor(TL: Point, BR: Point) : this(TL, (BR.x - TL.x), (BR.y - TL.y))

    constructor(origin: Point, width: Int, height: Int) : this(origin.x, origin.y, origin.x + width, origin.y + height)

    fun isInside(point: Point): Boolean =
        point.x >= origin.x && point.y >= origin.y &&
        point.x <= origin.x + width && point.y <= origin.y + height

    fun cover(point: Point): Rect =
        Rect(Point(minOf(x1, point.x), minOf(y1, point.y)), Point(maxOf(x2, point.x), maxOf(y2, point.y)))

    fun intersects(other: Rect) =
        !(other.x1 > this.x2 || other.x2 < this.x1 || other.y1 > this.y2 || other.y2 < this.y1)
}

data class Point(

    val x: Int,
    val y: Int

) : Comparable<Point> {

    override fun compareTo(other: Point): Int {
        if (x == other.x) return y.compareTo(other.y)
        return x.compareTo(other.x)
    }

    fun isLeftOfLine(from: Point, to: Point): Boolean {
        return crossProduct(from, to) > 0
    }

    fun crossProduct(origin: Point, p2: Point): Int {
        return (p2.x - origin.x) * (this.y - origin.y) - (p2.y - origin.y) * (this.x - origin.x)
    }

    fun distanceToLine(a: Point, b: Point): Double {
        return abs((b.x - a.x) * (a.y - this.y) - (a.x - this.x) * (b.y - a.y)) /
            sqrt((b.x - a.x).toDouble().pow(2.0) + (b.y - a.y).toDouble().pow(2.0))
    }

    fun euclideanDistanceTo(that: Point): Double {
        return EUCLIDEAN_DISTANCE_FUNC(this, that)
    }

    fun manhattanDistanceTo(that: Point): Double {
        return MANHATTAN_DISTANCE_FUNC(this, that)
    }

    companion object {
        // < 0 : Counterclockwise
        // = 0 : p, q and r are colinear
        // > 0 : Clockwise
        fun orientation(p: Point, q: Point, r: Point): Int {
            return (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y)
        }

        val EUCLIDEAN_DISTANCE_FUNC: (Point, Point) -> (Double) = { p, q ->
            val dx = p.x - q.x
            val dy = p.y - q.y
            sqrt((dx * dx + dy * dy).toDouble())
        }

        val MANHATTAN_DISTANCE_FUNC: (Point, Point) -> (Double) = { p, q ->
            val dx = p.x - q.x
            val dy = p.y - q.y
            sqrt((dx * dx + dy * dy).toDouble())
        }
    }
}
