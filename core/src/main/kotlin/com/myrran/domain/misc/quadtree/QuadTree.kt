package com.myrran.domain.misc.quadtree

class QuadNode<V>(

    private val NW: QuadTree<V>,
    private val NE: QuadTree<V>,
    private val SW: QuadTree<V>,
    private val SE: QuadTree<V>

): QuadTree<V> {

    override val frame: Rectangle =

        Rectangle(NW.frame.topLeft, SE.frame.bottomRight)

    constructor(frame: Rectangle):

        this(
            NW = QuadNil<V>(Rectangle(frame.origin, frame.width / 2, frame.height / 2)),
            NE = QuadNil<V>(Rectangle(Point(frame.x1 + frame.width / 2 + 1, frame.y1), frame.width / 2, frame.height / 2)),
            SW = QuadNil<V>(Rectangle(Point(frame.x1, frame.y1 + frame.height / 2 + 1), frame.width / 2, frame.height / 2)),
            SE = QuadNil<V>(Rectangle(frame.center, frame.width / 2, frame.height / 2))
        )

    override fun get(rect: Rectangle): Iterable<V> =

        (if (NW.frame.intersects(rect)) NW[rect] else emptyList()) +
        (if (NE.frame.intersects(rect)) NE[rect] else emptyList()) +
        (if (SW.frame.intersects(rect)) SW[rect] else emptyList()) +
        (if (SE.frame.intersects(rect)) SE[rect] else emptyList())


    override fun plus(pair: Pair<Point, V>): QuadTree<V> =

        QuadNode(
            NW = if (NW.frame.isInside(pair.first)) NW + pair else NW,
            NE = if (NE.frame.isInside(pair.first)) NE + pair else NE,
            SW = if (SW.frame.isInside(pair.first)) SW + pair else SW,
            SE = if (SE.frame.isInside(pair.first)) SE + pair else SE
        )
}

class QuadLeaf<V>(

    override val frame: Rectangle,
    val value: Pair<Point, V>

): QuadTree<V> {

    override fun get(rect: Rectangle): Iterable<V> =

        if (rect.isInside(value.first)) listOf(value.second)
        else emptyList()

    override fun plus(pair: Pair<Point, V>): QuadTree<V> =

        QuadNode<V>(frame.cover(pair.first)) + value + pair
}

class QuadNil<V>(

    override val frame: Rectangle

) : QuadTree<V> {

    override fun get(rect: Rectangle): Iterable<V> = emptyList()

    override fun plus(pair: Pair<Point, V>): QuadLeaf<V> =

        QuadLeaf(frame.cover(pair.first), value = pair)
}

interface QuadTree<V> {

    val frame: Rectangle
    operator fun get(rect: Rectangle): Iterable<V>
    operator fun plus(pair: Pair<Point, V>): QuadTree<V>
}

fun<V> emptyQuadTree(frame: Rectangle): QuadTree<V> = QuadNil(frame)

fun<V> quadTreeOf(frame: Rectangle, vararg pairs: Pair<Point, V>): QuadTree<V> {

    var empty = emptyQuadTree<V>(frame)
    for (pair in pairs) {
        empty += pair
    }
    return empty
}

