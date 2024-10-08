package com.myrran.infraestructure.view.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ai.GdxAI
import com.badlogic.gdx.graphics.GL20
import com.myrran.domain.world.World
import com.myrran.infraestructure.assets.AssetStorage
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.view.View
import ktx.app.KtxScreen

class MainScreen(

    private val assetStorage: AssetStorage,
    private val world: World,
    private val view: View,
    private val eventDispatcher: EventDispatcher,

): KtxScreen
{
    private var timeStep: Float = 0f
    private val fixedTimestep: Float = 0.015f

    override fun render(delta: Float) {

        clearScreen()

        GdxAI.getTimepiece().update(delta)
        eventDispatcher.update()

        timeStep += delta.coerceAtMost(0.3f)

        if (timeStep >= fixedTimestep) {

            world.saveLastPosition()

            while (timeStep >= fixedTimestep) {

                world.update(fixedTimestep)

                timeStep -= fixedTimestep
            }
        }

        view.render(delta, timeStep / fixedTimestep)
    }

    private fun clearScreen() {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun resize(width: Int, height: Int) {

        view.resize(width, height)
    }

    override fun dispose() {

        assetStorage.dispose()
        world.dispose()
        view.dispose()
    }
}
