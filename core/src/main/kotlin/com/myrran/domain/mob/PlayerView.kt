package com.myrran.domain.mob

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.myrran.domain.mob.metricunits.Pixel
import com.myrran.domain.mob.metricunits.Size
import com.myrran.infraestructure.view.mob.Pixie
import com.myrran.infraestructure.view.mob.player.PlayerAnimation

class PlayerView(

    private val model: Player,
    animations: Map<PlayerAnimation, Animation<TextureRegion>>,
    size: Size<Pixel>

): Steerable<Vector2> by model, Pixie<PlayerAnimation>(animations, PlayerAnimation.IDDLE, size)
