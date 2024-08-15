package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.domain.mobs.common.metrics.SizePixels
import ktx.actors.alpha

class SpriteStatic(

    private val texture: TextureRegion,

): Actor()
{
    init {

        val sizePixels = SizePixels(texture.regionWidth, texture.regionHeight)

        setSize(sizePixels.width.toFloat(), sizePixels.height.toFloat())
        setOrigin(sizePixels.width.toFloat()/2 , sizePixels.height.toFloat()/2)
    }

    override fun draw(batch: Batch, parentAlpha: Float) {

        batch.setColor(color.r,color.g, color.b, alpha)
        batch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}
