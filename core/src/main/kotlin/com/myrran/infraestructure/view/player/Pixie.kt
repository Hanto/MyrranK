package com.myrran.infraestructure.view.player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.domain.world.Pixel
import com.myrran.domain.world.Size

open class Pixie<ANIMATIOM_TYPES>(

    private val animations: Map<ANIMATIOM_TYPES, Animation<TextureRegion>>,
    private var currentAnimation: ANIMATIOM_TYPES,
    size: Size<Pixel>

): Actor()
{
    private var stateTime: Float = 0f

    init {

        this.setSize(size.width.toFloat(), size.height.toFloat())
        this.setOrigin((size.width / 2).toFloat() , (size.height / 2).toFloat() )
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun setAnimation(animation: ANIMATIOM_TYPES) {

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
