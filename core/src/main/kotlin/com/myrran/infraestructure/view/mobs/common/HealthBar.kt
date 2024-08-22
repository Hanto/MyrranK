package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.domain.entities.common.vulnerable.Vulnerable
import com.myrran.domain.misc.metrics.SizePixels
import ktx.actors.alpha

class HealthBar(

    private val vulnerable: Vulnerable,
    private val foreground: TextureRegion,
    private val background: TextureRegion,

): Actor()
{
    init {

        val sizePixels = background
            .let { SizePixels(it.regionWidth, it.regionHeight) }

        setSize(sizePixels.width.toFloat(), sizePixels.height.toFloat())
        setOrigin(sizePixels.width.toFloat()/2, sizePixels.height.toFloat()/2)

        color = Color.GREEN
        background.flip(true, false)
    }

    private var margin = 0
    private var blackBarMaxSize = width - margin*2
    private var blackBarSize = 0f
    private var blackBarX = 0f

    fun update() {

        val percentage = vulnerable.getHPs() / vulnerable.getMaxHps()
        blackBarSize = blackBarMaxSize * (1 - percentage )
        blackBarX = blackBarMaxSize - blackBarSize
    }

    override fun draw(batch: Batch, parentAlpha: Float) {

        batch.setColor(color.r,color.g, color.b, alpha)

        batch.draw(background, x, y, originX, originY, width, height, scaleX, scaleY, rotation)

        batch.draw(foreground, x + blackBarX + margin, y, originX, originY, blackBarSize, height, scaleX, scaleY, rotation)
    }
}
