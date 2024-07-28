package com.myrran.view.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.myrran.domain.skills.book.WorldSkillBook
import com.myrran.domain.utils.DeSerializer
import com.myrran.infraestructure.Repository
import com.myrran.infraestructure.adapters.SkillAdapter
import com.myrran.infraestructure.adapters.SkillBookAdapter
import com.myrran.infraestructure.adapters.SkillTemplateAdapter
import com.myrran.view.atlas.Atlas
import ktx.app.KtxScreen

class MainScreen(

    private val batch: SpriteBatch = SpriteBatch(),
    private val uiStage: Stage = Stage(),
    private val repository: Repository = Repository(SkillBookAdapter(SkillAdapter(), SkillTemplateAdapter()), DeSerializer()),
    private val atlas: Atlas = repository.loadAtlas(),
    private val worldSkillBook: WorldSkillBook = repository.loadSkillBook(),

): KtxScreen
{
    private val fpsText: Label

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        val fpsStyle = LabelStyle(atlas.retrieveFont("20"), Color.WHITE)
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
