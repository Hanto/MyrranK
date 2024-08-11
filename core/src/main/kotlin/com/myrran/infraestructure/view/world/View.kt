package com.myrran.infraestructure.view.world

import com.badlogic.gdx.graphics.g2d.SpriteBatch

class View(

    private val worldView: WorldView,
    private val uiView: UIView,
    private val batch: SpriteBatch,
)
{
    private var timeStep: Float = 0f
    private val fixedTimestep: Float = 0.015f

    fun render(delta: Float) {

        batch.begin()
        batch.end()

        worldView.render(delta, timeStep / fixedTimestep)
        uiView.render(delta)
    }
}
