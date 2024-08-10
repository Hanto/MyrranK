package com.myrran.infraestructure.view.mob.player

import com.badlogic.gdx.graphics.g2d.Animation
import com.myrran.domain.mob.metricunits.Pixel
import com.myrran.domain.mob.metricunits.Size
import com.myrran.infraestructure.view.mob.Pixie
import ktx.collections.toGdxArray

class PlayerFactory
{
    companion object {

        private val size = Size(Pixel(32f), Pixel(32f))
    }

    fun toPlayerView(assets: PlayerViewAssets): Pixie<PlayerAnimation> {

        val frames = assets.characterTexture.split(size.width.value(), size.height.value())
        val animations = mapOf(
            PlayerAnimation.WALK_DOWN to    Animation(0.3f, arrayOf(frames[0][0], frames[0][1], frames[0][2]).toGdxArray()),
            PlayerAnimation.WALK_LEFT to    Animation(0.3f, arrayOf(frames[1][0], frames[1][1], frames[1][2]).toGdxArray()),
            PlayerAnimation.WALK_RIGHT to   Animation(0.3f, arrayOf(frames[2][0], frames[2][1], frames[2][2]).toGdxArray()),
            PlayerAnimation.WALK_UP to      Animation(0.3f, arrayOf(frames[3][0], frames[3][1], frames[3][2]).toGdxArray()),
            PlayerAnimation.IDDLE to        Animation(0.5f, arrayOf(frames[4][0], frames[4][1], frames[4][2]).toGdxArray())
        )

        return Pixie(animations, PlayerAnimation.IDDLE, size)
    }
}
