package com.myrran.view.zmain

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.myrran.domain.skills.book.WorldSkillBook
import com.myrran.domain.skills.skills.stat.BonusPerUpgrade
import com.myrran.domain.skills.skills.stat.NumUpgrades
import com.myrran.domain.skills.skills.stat.StatBonus
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.StatName
import com.myrran.domain.skills.skills.stat.StatUpgradeable
import com.myrran.domain.skills.skills.stat.UpgradeCost
import com.myrran.domain.skills.skills.stat.Upgrades
import com.myrran.domain.utils.DeSerializer
import com.myrran.infraestructure.Repository
import com.myrran.infraestructure.adapters.SkillAdapter
import com.myrran.infraestructure.adapters.SkillBookAdapter
import com.myrran.infraestructure.adapters.SkillTemplateAdapter
import com.myrran.view.atlas.Atlas
import com.myrran.view.ui.WidgetText
import com.myrran.view.ui.skill.StatView
import ktx.app.KtxScreen

class MainScreen(

    private val batch: SpriteBatch = SpriteBatch(),
    private val uiStage: Stage = Stage(),
    private val atlas: Atlas = Atlas(
        assetManager = AssetManager()),

    private val repository: Repository = Repository(
        skillBookAdapter = SkillBookAdapter(
            skillAdapter = SkillAdapter(),
            skillTemplateAdapter = SkillTemplateAdapter()),
        deSerializer =  DeSerializer()),

    private val worldSkillBook: WorldSkillBook = repository.loadSkillBook(),

): KtxScreen
{
    private val fpsText: WidgetText<String>

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        val initialAssets = repository.loadAssetCollection("InitialAssets.json")
        atlas.load(initialAssets)
        atlas.finishLoading()

        fpsText = WidgetText("FPS: ?", atlas.getFont("20.fnt"), shadowTickness = 2f, formater = {it.toString()})
        uiStage.addActor(fpsText)

        val stat = StatUpgradeable(
            id = StatId("SPEED"),
            name = StatName("Speed"),
            baseBonus = StatBonus(50.0f),
            upgrades = Upgrades(
                actual = NumUpgrades(10),
                maximum = NumUpgrades(20)
            ),
            upgradeCost = UpgradeCost(2.0f),
            bonusPerUpgrade = BonusPerUpgrade(2.0f)
        )

        val font14 = atlas.getFont("14.fnt")
        val font12 = atlas.getFont("Calibri12.fnt")
        val font10 = atlas.getFont("Arial10.fnt")

        val statView = StatView(stat, font14, font12, font10)
        uiStage.addActor(statView)
        statView.setPosition(200f, 100f)
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

        fpsText.setText("Hola Mundo: ${Gdx.graphics.framesPerSecond}")
    }

    private fun clearScreen() {

        Gdx.gl.glClearColor(0.55f, 0.05f, 0.05f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun dispose() {

        batch.dispose()
        uiStage.dispose()
        atlas.dispose()
    }
}
