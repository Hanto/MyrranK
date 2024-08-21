package com.myrran.infraestructure.view.mobs.player

import box2dLight.PointLight
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.mob.player.Player
import com.myrran.domain.entities.mob.player.StateIddle
import com.myrran.domain.entities.mob.player.StateMoving
import com.myrran.domain.entities.mob.player.StateTacticalCasting
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.infraestructure.view.mobs.common.CastingBar
import com.myrran.infraestructure.view.mobs.common.MobView
import com.myrran.infraestructure.view.mobs.common.SpriteAnimated
import com.myrran.infraestructure.view.mobs.common.SpriteStatic

class PlayerView(

    private val model: Player,
    private val character: SpriteAnimated<PlayerAnimation>,
    private val shadow: SpriteStatic,
    private val castingBar: CastingBar,
    private val light: PointLight,

): Group(), MobView, Identifiable<EntityId>, Disposable
{
    override val id: EntityId = model.id

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        addActor(shadow)
        addActor(character)
        addActor(castingBar)
        setSize(character.width, character.height)
        setOrigin(character.width/2, character.height/2)
        shadow.moveBy(0f, -5f)
        castingBar.moveBy(-2f, 36f)
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun updatePosition(fractionOfTimestep: Float) {

        model.getInterpolatedPosition(fractionOfTimestep)
            .let { PositionMeters(it.x, it.y).toPixels() }
            .also { setPosition(it.x.toFloat(), it.y.toFloat(), Align.center) }
    }

    override fun act(deltaTime: Float) {

        when (model.state) {

            is StateIddle -> character.setAnimation(PlayerAnimation.IDDLE)
            is StateTacticalCasting -> character.setAnimation(PlayerAnimation.CASTING)
            is StateMoving -> setWalkingAnimation()
        }

        castingBar.update()
        super.act(deltaTime)
    }

    override fun dispose() =

        light.remove()

    // ANIMATION:
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

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun Player.goesNorth() = linearVelocity.y > 0.2f
    private fun Player.goesSouth() = linearVelocity.y < -0.2f
    private fun Player.goesWest() = linearVelocity.x < -0.2f
    private fun Player.goesEast() = linearVelocity.x > 0.2f

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
