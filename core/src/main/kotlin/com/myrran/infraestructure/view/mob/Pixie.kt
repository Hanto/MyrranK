package com.myrran.infraestructure.view.mob

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.domain.mob.metricunits.SizePixels

open class Pixie<ANIMATIOM_TYPE: Enum<ANIMATIOM_TYPE>>(

    private val animations: Map<ANIMATIOM_TYPE, Animation<TextureRegion>>,
    private var currentAnimation: ANIMATIOM_TYPE,
    sizePixels: SizePixels

): Actor()
{
    private var stateTime: Float = 0f

    init {

        val sizeMeters = sizePixels.toMeters()

        this.setSize(sizeMeters.width.toFloat(), sizeMeters.height.toFloat())
        this.setOrigin((sizeMeters.width / 2).toFloat() , (sizePixels.height / 2).toFloat() )
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun setAnimation(animation: ANIMATIOM_TYPE) {

        if (animation != currentAnimation) {

            currentAnimation = animation
            stateTime = 0f
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {

        stateTime += Gdx.graphics.deltaTime
        val frame = animations[currentAnimation]!!.getKeyFrame(stateTime, true)

        batch.draw(frame, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}
