package com.myrran.infraestructure.view.mobs.player

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.myrran.domain.mobs.common.metrics.SizePixels
import com.myrran.domain.mobs.player.Player
import com.myrran.domain.mobs.player.StateIddle
import com.myrran.domain.mobs.player.StateMoving
import com.myrran.domain.mobs.player.StateTacticalCasting
import com.myrran.infraestructure.view.mobs.common.Sprite

class PlayerView(

    private val model: Player,
    animations: Map<PlayerAnimation, Animation<TextureRegion>>,
    size: SizePixels

): Sprite<PlayerAnimation>(animations, PlayerAnimation.IDDLE, size)
{
    fun update(fractionOfTimestep: Float) {

        when (model.state) {
            is StateIddle -> setAnimation(PlayerAnimation.IDDLE)
            is StateTacticalCasting -> setAnimation(PlayerAnimation.CASTING)
            is StateMoving-> setWalkingAnimation()
        }

        model.getInterpolatedPosition(fractionOfTimestep)
            .also { setPosition(it.x, it.y) }
    }

    private var oldDirection: Direction = Direction.STOP

    private fun setWalkingAnimation() {

        val newDirection = when (oldDirection) {

            Direction.STOP -> {

                model.direction()
            }
            Direction.NORTH -> when {

                model.goesNorth() -> Direction.NORTH
                else -> model.direction()
            }
            Direction.SOUTH -> when {

                model.goesSouth() -> Direction.SOUTH
                else -> model.direction()
            }
            Direction.EAST -> when {

                model.goesEast() -> Direction.EAST
                else -> model.direction()
            }
            Direction.WEST -> when {

                model.goesWest() -> Direction.WEST
                else -> model.direction()
            }
        }

        setAnimation(newDirection.animation)
        oldDirection = newDirection
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun Player.goesNorth() = linearVelocity.y > 0
    private fun Player.goesSouth() = linearVelocity.y < 0
    private fun Player.goesWest() = linearVelocity.x < 0
    private fun Player.goesEast() = linearVelocity.x > 0

    private fun Player.direction(): Direction =

        when{
            goesEast() -> Direction.EAST
            goesWest() -> Direction.WEST
            goesNorth() -> Direction.NORTH
            goesSouth() -> Direction.SOUTH
            else -> Direction.STOP
        }

    private enum class Direction(val animation: PlayerAnimation) {
        NORTH(PlayerAnimation.WALK_NORTH),
        SOUTH(PlayerAnimation.WALK_SOUTH),
        EAST(PlayerAnimation.WALK_EAST),
        WEST(PlayerAnimation.WALK_WEST),
        STOP(PlayerAnimation.IDDLE)
    }
}

