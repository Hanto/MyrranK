package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.myrran.domain.mobs.common.caster.Caster
import com.myrran.domain.mobs.common.metrics.SizePixels
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

        alpha = 0f
    }

    private var state = State.NOT_CASTING
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

            blackBarSize = width * (1 - caster.getCastingInfo().percentage)
            blackBarX = width - blackBarSize
        }
        else
        {
            if (state == State.CASTING)
            {
                clearActions()
                addAction(Actions.fadeOut(1f, Interpolation.circleOut))
                state = State.NOT_CASTING

                blackBarSize = width
                blackBarX = width - blackBarSize
            }
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {

        batch.setColor(color.r,color.g, color.b, alpha)

        if (caster.isCasting())
            batch.draw(background, x, y, originX, originY, width, height, scaleX, scaleY, rotation)

        batch.draw(foreground, x + blackBarX, y, originX, originY, blackBarSize, height, scaleX, scaleY, rotation)
    }

    private enum class State { NOT_CASTING, CASTING }
}
