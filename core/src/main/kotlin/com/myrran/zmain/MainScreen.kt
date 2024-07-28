package com.myrran.zmain

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.myrran.view.Atlas
import ktx.app.KtxScreen

class MainScreen: KtxScreen
{
    private val batch: SpriteBatch = SpriteBatch()
    private val uiStage: Stage = Stage()
    private val atlas: Atlas = Atlas()
    private var fpsText: Label

    init {

        val fpsStyle = LabelStyle(atlas.fonts["20"], Color.WHITE)
        fpsText = Label("fps", fpsStyle)
        uiStage.addActor(fpsText)
    }

    // RENDER:
    //--------------------------------------------------------------------------------------------------------

    override fun render(delta: Float) {

        clearScreen()

        batch.begin()
        renderWorld(delta)
        batch.end()

        uiStage.act()
        renderUI(delta)
        uiStage.draw()
    }

    private fun renderWorld(delta: Float) {

    }

    private fun renderUI(delta: Float) {

        fpsText.setText("fps: ${Gdx.graphics.framesPerSecond}")
    }

    private fun clearScreen() {

        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun dispose() {

        batch.dispose()
        uiStage.dispose()
        atlas.dispose()
    }
}
