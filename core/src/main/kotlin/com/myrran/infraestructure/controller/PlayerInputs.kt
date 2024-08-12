package com.myrran.infraestructure.controller

import com.badlogic.gdx.math.Vector2
import ktx.math.plus

data class PlayerInputs(

    var doCast: Boolean = false,
    var goNorth: Boolean = false,
    var goSouth: Boolean = false,
    var goEast: Boolean = false,
    var goWest: Boolean = false,
)
{
    companion object {

        private val NORTH = Vector2(0f, 1f)
        private val SOUTH = Vector2(0f, -1f)
        private val EAST = Vector2(1f, 0f)
        private val WEST = Vector2(-1f, 0f)
    }

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

        return forces.fold(Vector2(0f, 0f)) { acc, next -> acc.plus(next) }.nor()
    }

    private fun Vector2.goesNorth() = this.y > 0
    private fun Vector2.goesEast() = this.x > 0
}
