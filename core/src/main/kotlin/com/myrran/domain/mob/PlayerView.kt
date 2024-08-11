package com.myrran.domain.mob

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.myrran.domain.mob.metricunits.SizePixels
import com.myrran.infraestructure.input.StateEast
import com.myrran.infraestructure.input.StateIddle
import com.myrran.infraestructure.input.StateNorth
import com.myrran.infraestructure.input.StateSouth
import com.myrran.infraestructure.input.StateWest
import com.myrran.infraestructure.view.mob.Pixie
import com.myrran.infraestructure.view.mob.player.PlayerAnimation
import ktx.math.minus

class PlayerView(

    private val model: Player,
    animations: Map<PlayerAnimation, Animation<TextureRegion>>,
    size: SizePixels

): Steerable<Vector2> by model, Pixie<PlayerAnimation>(animations, PlayerAnimation.IDDLE, size)
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
