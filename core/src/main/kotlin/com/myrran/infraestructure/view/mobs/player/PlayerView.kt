package com.myrran.infraestructure.view.mobs.player

import box2dLight.PointLight
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.player.Player
import com.myrran.domain.mobs.player.StateIddle
import com.myrran.domain.mobs.player.StateMoving
import com.myrran.domain.mobs.player.StateTacticalCasting
import com.myrran.infraestructure.view.mobs.common.SpriteAnimated
import com.myrran.infraestructure.view.mobs.common.StaticSprite

class PlayerView(

    private val model: Player,
    private val character: SpriteAnimated<PlayerAnimation>,
    private val sombra: StaticSprite,
    private val light: PointLight,

): Group(), Disposable
{
    init {

        addActor(sombra)
        addActor(character)
        setOrigin(character.width/2, character.height/2)
        sombra.moveBy(0f, Pixel(-5).toBox2DUnits())
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update(fractionOfTimestep: Float) {

        when (model.state) {
            is StateIddle -> character.setAnimation(PlayerAnimation.IDDLE)
            is StateTacticalCasting -> character.setAnimation(PlayerAnimation.CASTING)
            is StateMoving-> setWalkingAnimation()
        }

        model.getInterpolatedPosition(fractionOfTimestep)
            .also { setPosition(it.x, it.y) }
    }

    // WALKING ANIMATION:
    //--------------------------------------------------------------------------------------------------------

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

        character.setAnimation(newDirection.animation)
        oldDirection = newDirection
    }

    override fun dispose() =

        light.remove()

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
