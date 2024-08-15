package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.domain.mobs.common.caster.Caster
import com.myrran.domain.mobs.common.metrics.Pixel
import ktx.actors.alpha

class CastingBar(

    private val caster: Caster,
    private val foreground: TextureRegion,
    private val background: TextureRegion,

): Actor()
{
    var foregroundWidth = 0f

    init {

        setSize(36f, 5f)
        setOrigin(18f,  0f)
    }

    fun update() {

        val size = Pixel(29).toFloat() * 1f //caster.getCastingInfo().percentage
        foregroundWidth = size
    }

    override fun draw(batch: Batch, parentAlpha: Float) {

        batch.setColor(color.r,color.g, color.b, alpha)
        batch.draw(background, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}
