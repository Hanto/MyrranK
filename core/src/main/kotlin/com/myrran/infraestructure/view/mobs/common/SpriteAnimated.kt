package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import ktx.actors.alpha

open class SpriteAnimated<ANIMATIOM_TYPE: Enum<ANIMATIOM_TYPE>>(

    private val animations: Map<ANIMATIOM_TYPE, Animation<TextureRegion>>,
    private var currentAnimation: ANIMATIOM_TYPE,

): Actor()
{
    private var stateTime: Float = 0f

    init {

        val sizeMeters = animations.values.first().keyFrames.first()
            .let { Vector2(it.regionWidth.toFloat(), it.regionHeight.toFloat()) }

        this.setSize(sizeMeters.x, sizeMeters.y)
        this.setOrigin(sizeMeters.x/2 , sizeMeters.y/2 )
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
