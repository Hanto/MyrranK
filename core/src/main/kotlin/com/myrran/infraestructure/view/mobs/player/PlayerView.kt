package com.myrran.infraestructure.view.mobs.player

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.myrran.domain.mobs.common.metrics.SizePixels
import com.myrran.domain.mobs.player.Player
import com.myrran.domain.mobs.player.StateEast
import com.myrran.domain.mobs.player.StateIddle
import com.myrran.domain.mobs.player.StateNorth
import com.myrran.domain.mobs.player.StateSouth
import com.myrran.domain.mobs.player.StateWest
import com.myrran.infraestructure.view.mobs.common.Sprite

class PlayerView(

    private val model: Player,
    animations: Map<PlayerAnimation, Animation<TextureRegion>>,
    size: SizePixels

): Sprite<PlayerAnimation>(animations, PlayerAnimation.IDDLE, size)
{
    fun update(fractionOfTimestep: Float) {

        when (model.state) {
            is StateEast -> setAnimation(PlayerAnimation.WALK_EAST)
            is StateWest -> setAnimation(PlayerAnimation.WALK_WEST)
            is StateNorth -> setAnimation(PlayerAnimation.WALK_NORTH)
            is StateSouth -> setAnimation(PlayerAnimation.WALK_SOUTH)
            is StateIddle -> setAnimation(PlayerAnimation.IDDLE)
        }

        model.getInterpolatedPosition(fractionOfTimestep)
            .also { setPosition(it.x, it.y) }
    }
}
