package com.myrran.infraestructure.view

import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Disposable

class View(

    private val worldView: WorldView,
    private val uiView: UIView,
    private val inputMultiplexer: InputMultiplexer,
    private val batch: SpriteBatch,

    ): Disposable
{

    fun render(delta: Float, fractionOfTimestamp: Float) {

        batch.begin()
        batch.end()

        worldView.render(delta, fractionOfTimestamp)
        uiView.render(delta)
    }

    fun resize(width: Int, height: Int) {

        uiView.resize(width, height)
    }

    override fun dispose() {

        worldView.dispose()
        uiView.dispose()
        batch.dispose()
    }
}
