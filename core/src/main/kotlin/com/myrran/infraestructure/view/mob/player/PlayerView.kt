package com.myrran.infraestructure.view.mob.player

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.myrran.domain.mob.metrics.SizePixels
import com.myrran.domain.mob.player.Player
import com.myrran.domain.mob.player.StateEast
import com.myrran.domain.mob.player.StateIddle
import com.myrran.domain.mob.player.StateNorth
import com.myrran.domain.mob.player.StateSouth
import com.myrran.domain.mob.player.StateWest
import com.myrran.infraestructure.view.mob.Pixie
import ktx.math.minus

class PlayerView(

    private val model: Player,
    animations: Map<PlayerAnimation, Animation<TextureRegion>>,
    size: SizePixels

): Pixie<PlayerAnimation>(animations, PlayerAnimation.IDDLE, size)
{
    fun update(fractionOfTimestep: Float) {

        when (model.state) {
            is StateEast -> setAnimation(PlayerAnimation.WALK_EAST)
            is StateWest -> setAnimation(PlayerAnimation.WALK_WEST)
            is StateNorth -> setAnimation(PlayerAnimation.WALK_NORTH)
            is StateSouth -> setAnimation(PlayerAnimation.WALK_SOUTH)
            is StateIddle -> setAnimation(PlayerAnimation.IDDLE)
        }

        val offset = model.position.minus(model.lastPosition)

        setPosition(
            model.lastPosition.x + offset.x * fractionOfTimestep,
            model.lastPosition.y + offset.y * fractionOfTimestep)
    }
}
