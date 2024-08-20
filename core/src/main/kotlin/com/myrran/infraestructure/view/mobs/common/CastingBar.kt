package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.myrran.domain.entities.common.caster.Caster
import com.myrran.domain.misc.metrics.SizePixels
import ktx.actors.alpha

class CastingBar(

    private val caster: Caster,
    private val foreground: TextureRegion,
    private val background: TextureRegion,

): Actor()
{
    init {

        val sizePixels = background
            .let { SizePixels(it.regionWidth, it.regionHeight) }

        setSize(sizePixels.width.toFloat(), sizePixels.height.toFloat())
        setOrigin(sizePixels.width.toFloat()/2, sizePixels.height.toFloat()/2)

        background.flip(true, false)
        alpha = 0f
    }

    private var state = State.NOT_CASTING
    private var margin = 0
    private var blackBarMaxSize = width - margin*2
    private var blackBarSize = 0f
    private var blackBarX = 0f

    fun update() {

        if (caster.isCasting()) {

            if (state == State.NOT_CASTING)
            {
                clearActions()
                addAction(Actions.fadeIn(0.3f, Interpolation.circleOut))
                state = State.CASTING
            }

            blackBarSize = blackBarMaxSize * (1 - caster.getCastingInfo().percentage)
            blackBarX = blackBarMaxSize - blackBarSize
        }
        else
        {
            if (state == State.CASTING)
            {
                clearActions()
                addAction(Actions.fadeOut(1f, Interpolation.circleOut))
                state = State.NOT_CASTING

                blackBarSize = blackBarMaxSize
                blackBarX = blackBarMaxSize - blackBarSize
            }
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {

        batch.setColor(color.r,color.g, color.b, alpha)

        if (caster.isCasting())
            batch.draw(background, x, y, originX, originY, width, height, scaleX, scaleY, rotation)

        batch.draw(foreground, x + blackBarX + margin, y, originX, originY, blackBarSize, height, scaleX, scaleY, rotation)
    }

    private enum class State { NOT_CASTING, CASTING }
}
