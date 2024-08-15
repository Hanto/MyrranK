package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.domain.mobs.common.caster.Caster
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.common.metrics.SizePixels
import ktx.actors.alpha

class CastingBar(

    private val caster: Caster,
    private val foreground: TextureRegion,
    private val background: TextureRegion,

): Actor()
{
    var foregroundWidth = 0f

    init {

        val sizeMeters = SizePixels(36, 5).toMeters()

        setSize(sizeMeters.width.toFloat(), sizeMeters.height.toFloat())
        setOrigin((sizeMeters.width/2).toFloat() , (sizeMeters.height/2).toFloat() )
    }

    fun update() {

        val size = Pixel(29).toBox2DUnits() * 1f //caster.getCastingInfo().percentage
        foregroundWidth = size
    }

    override fun draw(batch: Batch, parentAlpha: Float) {

        batch.setColor(color.r,color.g, color.b, alpha)
        batch.draw(background, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
        //batch.draw(foreground, x + Pixel(1).toBox2DUnits(), y, originX, originY, foregroundWidth, height, scaleX, scaleY, rotation)
    }
}
