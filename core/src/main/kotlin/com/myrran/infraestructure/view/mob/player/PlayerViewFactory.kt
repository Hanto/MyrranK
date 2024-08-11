package com.myrran.infraestructure.view.mob.player

import com.badlogic.gdx.graphics.g2d.Animation
import com.myrran.domain.mob.Player
import com.myrran.domain.mob.PlayerView
import com.myrran.domain.mob.metrics.SizePixels
import ktx.collections.toGdxArray

class PlayerViewFactory
{
    companion object {

        private val size = SizePixels(32, 32)
    }

    fun toPlayerView(model: Player, assets: PlayerViewAssets): PlayerView {

        val frames = assets.characterTexture.split(size.width.value(), size.height.value())
        val animations = mapOf(
            PlayerAnimation.WALK_SOUTH to    Animation(0.2f, arrayOf(frames[0][0], frames[0][1], frames[0][2]).toGdxArray()),
            PlayerAnimation.WALK_WEST to    Animation(0.2f, arrayOf(frames[1][0], frames[1][1], frames[1][2]).toGdxArray()),
            PlayerAnimation.WALK_EAST to   Animation(0.2f, arrayOf(frames[2][0], frames[2][1], frames[2][2]).toGdxArray()),
            PlayerAnimation.WALK_NORTH to      Animation(0.2f, arrayOf(frames[3][0], frames[3][1], frames[3][2]).toGdxArray()),
            PlayerAnimation.IDDLE to        Animation(0.5f, arrayOf(frames[2][3], frames[2][4], frames[2][5]).toGdxArray())
        )

        return PlayerView(model, animations, size)
    }
}
