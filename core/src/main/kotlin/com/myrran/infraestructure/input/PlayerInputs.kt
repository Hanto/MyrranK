package com.myrran.infraestructure.input

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.mob.metricunits.Pixel
import com.myrran.domain.mob.metricunits.Position
import ktx.math.plus

data class PlayerInputs(

    var touched: Position<Pixel> = Position(Pixel(0f), Pixel(0f)),
    var goNorth: Boolean = false,
    var goSouth: Boolean = false,
    var goEast: Boolean = false,
    var goWest: Boolean = false,
)
{
    fun calculateDirection(oldDirection: Vector2): Vector2 {

        val forces:MutableList<Vector2> = mutableListOf()

        if (oldDirection.goesNorth()) {
            when {
                goNorth -> forces.add(NORTH)
                goSouth -> forces.add(SOUTH)
            }
        }
        else {
            when {
                goSouth -> forces.add(SOUTH)
                goNorth -> forces.add(NORTH)
            }
        }
        if (oldDirection.goesEast()) {
            when {
                goEast -> forces.add(EAST)
                goWest -> forces.add(WEST)
            }
        }
        else {
            when {
                goWest -> forces.add(WEST)
                goEast -> forces.add(EAST)
            }
        }

        return forces.fold( Vector2(0f, 0f) ) { acc, next -> acc.plus(next) }.nor()
    }
}

private val NORTH = Vector2(0f, 1f)
private val SOUTH = Vector2(0f, -1f)
private val EAST = Vector2(1f, 0f)
private val WEST = Vector2(-1f, 0f)

private fun Vector2.goesNorth() = this.y > 0
private fun Vector2.goesEast() = this.x > 0
