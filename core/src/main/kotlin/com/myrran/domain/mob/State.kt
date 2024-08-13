package com.myrran.domain.mob

import com.badlogic.gdx.math.Vector2
import com.myrran.infraestructure.controller.PlayerInputs

data class StateIddle(override val direction: Vector2): State {

    override fun nextState(inputs: PlayerInputs): State {

        val nextDirection = inputs.calculateDirection(direction)

        return when {
            nextDirection.goesEast() -> StateEast(nextDirection)
            nextDirection.goesWest() -> StateWest(nextDirection)
            nextDirection.goesNorth() -> StateNorth(nextDirection)
            nextDirection.goesSouth() -> StateSouth(nextDirection)
            else -> StateIddle(nextDirection)
        }
    }
}

data class StateEast(override val direction: Vector2): State {

    override fun nextState(inputs: PlayerInputs): State {

        val nextDirection = inputs.calculateDirection(direction)

        return when {
            nextDirection.goesEast() -> StateEast(nextDirection)
            nextDirection.goesWest() -> StateWest(nextDirection)
            nextDirection.goesNorth() -> StateNorth(nextDirection)
            nextDirection.goesSouth() -> StateSouth(nextDirection)
            else -> StateIddle(nextDirection)
        }
    }
}

data class StateWest(override val direction: Vector2): State {

    override fun nextState(inputs: PlayerInputs): State {

        val nextDirection = inputs.calculateDirection(direction)

        return when {
            nextDirection.goesEast() -> StateEast(nextDirection)
            nextDirection.goesWest() -> StateWest(nextDirection)
            nextDirection.goesNorth() -> StateNorth(nextDirection)
            nextDirection.goesSouth() -> StateSouth(nextDirection)
            else -> StateIddle(nextDirection)
        }
    }
}

data class StateNorth(override val direction: Vector2): State {

    override fun nextState(inputs: PlayerInputs): State {

        val nextDirection = inputs.calculateDirection(direction)

        return when {
            nextDirection.goesNorth() -> StateNorth(nextDirection)
            nextDirection.goesSouth() -> StateSouth(nextDirection)
            nextDirection.goesEast() -> StateEast(nextDirection)
            nextDirection.goesWest() -> StateWest(nextDirection)
            else -> StateIddle(nextDirection)
        }
    }
}

data class StateSouth(override val direction: Vector2): State {

    override fun nextState(inputs: PlayerInputs): State {

        val nextDirection = inputs.calculateDirection(direction)

        return when {
            nextDirection.goesNorth() -> StateNorth(nextDirection)
            nextDirection.goesSouth() -> StateSouth(nextDirection)
            nextDirection.goesEast() -> StateEast(nextDirection)
            nextDirection.goesWest() -> StateWest(nextDirection)
            else -> StateIddle(nextDirection)
        }
    }
}

sealed interface State {

    val direction: Vector2
    fun nextState(inputs: PlayerInputs): State
}

private fun Vector2.goesNorth() = this.y > 0
private fun Vector2.goesSouth() = this.y < 0
private fun Vector2.goesWest() = this.x < 0
private fun Vector2.goesEast() = this.x > 0
