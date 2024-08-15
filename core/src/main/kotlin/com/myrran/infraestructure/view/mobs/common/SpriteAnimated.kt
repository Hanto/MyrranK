package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.domain.mobs.common.metrics.SizePixels
import ktx.actors.alpha

open class SpriteAnimated<ANIMATIOM_TYPE: Enum<ANIMATIOM_TYPE>>(

    private val animations: Map<ANIMATIOM_TYPE, Animation<TextureRegion>>,
    private var currentAnimation: ANIMATIOM_TYPE,

): Actor()
{
    private var stateTime: Float = 0f

    init {

        val sizePixels = animations.values.first().keyFrames.first()
            .let { SizePixels(it.regionWidth, it.regionHeight) }

        this.setSize(sizePixels.width.toFloat(), sizePixels.height.toFloat())
        this.setOrigin(sizePixels.width.toFloat()/2 , sizePixels.height.toFloat()/2 )
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

        batch.setColor(color.r,color.g, color.b, alpha)
        batch.draw(frame, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }
}
