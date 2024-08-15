package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.domain.mobs.common.metrics.SizePixels
import ktx.actors.alpha

class StaticSprite(

    private val texture: TextureRegion,

): Actor()
{
    init {

        val sizeMeters = SizePixels(texture.regionWidth, texture.regionHeight).toMeters()

        this.setSize(sizeMeters.width.toFloat(), sizeMeters.height.toFloat())
        this.setOrigin((sizeMeters.width/2).toFloat() , (sizeMeters.height/2).toFloat() )
    }

    override fun draw(batch: Batch, parentAlpha: Float) {

        batch.setColor(color.r,color.g, color.b, alpha)
        batch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}
