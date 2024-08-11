package com.myrran.infraestructure.view.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.myrran.application.World
import com.myrran.infraestructure.assets.AssetStorage
import com.myrran.infraestructure.view.world.View
import ktx.app.KtxScreen

class MainScreen(

    private val assetStorage: AssetStorage,
    private val world: World,
    private val view: View,

): KtxScreen
{
    private var timeStep: Float = 0f
    private val fixedTimestep: Float = 0.015f

    override fun render(delta: Float) {

        clearScreen()

        timeStep += delta

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
