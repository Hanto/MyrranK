package com.myrran.infraestructure.view.player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.myrran.infraestructure.view.Pixel
import ktx.collections.toGdxArray

class PlayerView(

    val assets: PlayerViewAssets

): Actor()
{
    companion object {

        private val WIDTH = Pixel(32)
        private val HEIGHT = Pixel(32)
    }

    private val animations: Map<PlayerAnimation, Animation<TextureRegion>>
    private var stateTime: Float = 0f
    private var currentAnimation: PlayerAnimation = PlayerAnimation.WALK_RIGHT

    init {

        val frames = assets.characterTexture.split(WIDTH.value, HEIGHT.value)
        setSize(WIDTH.value.toFloat(), HEIGHT.value.toFloat())

        animations = mapOf(
            PlayerAnimation.WALK_DOWN to    Animation(0.3f, arrayOf(frames[0][0], frames[0][1], frames[0][2]).toGdxArray()),
            PlayerAnimation.WALK_LEFT to    Animation(0.3f, arrayOf(frames[1][0], frames[1][1], frames[1][2]).toGdxArray()),
            PlayerAnimation.WALK_RIGHT to   Animation(0.3f, arrayOf(frames[2][0], frames[2][1], frames[2][2]).toGdxArray()),
            PlayerAnimation.WALK_UP to      Animation(0.3f, arrayOf(frames[3][0], frames[3][1], frames[3][2]).toGdxArray()),
            PlayerAnimation.IDDLE to        Animation(0.5f, arrayOf(frames[4][0], frames[4][1], frames[4][2]).toGdxArray())
        )
    }

    fun setAnimation(animation: PlayerAnimation) {

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
