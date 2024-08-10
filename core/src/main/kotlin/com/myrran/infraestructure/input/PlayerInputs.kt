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
                goNorth && goSouth -> forces.add(NORTH)
                goNorth -> forces.add(NORTH)
                goSouth -> forces.add(SOUTH)
            }
        }
        else if (oldDirection.goesSouth()) {
            when {
                goNorth && goSouth -> forces.add(SOUTH)
                goNorth -> forces.add(NORTH)
                goSouth -> forces.add(SOUTH)
            }
        }
        else {
            when {
                goNorth && goSouth -> Unit
                goNorth -> forces.add(NORTH)
                goSouth -> forces.add(SOUTH)
            }
        }

        if (oldDirection.goesEast()) {
            when {
                goWest && goEast -> forces.add(EAST)
                goWest -> forces.add(WEST)
                goEast -> forces.add(EAST)
            }
        }
        else if (oldDirection.goesWest()) {
            when {
                goWest && goEast -> forces.add(WEST)
                goWest -> forces.add(WEST)
                goEast -> forces.add(EAST)
            }
        }
        else {
            when {
                goWest && goEast -> Unit
                goWest -> forces.add(WEST)
                goEast -> forces.add(EAST)
            }
        }

        return forces.fold( Vector2(0f, 0f)) { acc, next -> acc.plus(next) }.nor()
    }
}

private val NORTH = Vector2(0f, 1f)
private val SOUTH = Vector2(0f, -1f)
private val EAST = Vector2(1f, 0f)
private val WEST = Vector2(-1f, 0f)

private fun Vector2.goesNorth() = this.y > 0
private fun Vector2.goesSouth() = this.y < 0
private fun Vector2.goesWest() = this.x < 0
private fun Vector2.goesEast() = this.x > 0
