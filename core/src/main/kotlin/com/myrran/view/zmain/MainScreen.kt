package com.myrran.view.zmain

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.myrran.badlogic.DaD
import com.myrran.controller.DragAndDropManager
import com.myrran.controller.SkillController
import com.myrran.domain.skills.book.PlayerSkillBook
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.utils.DeSerializer
import com.myrran.infraestructure.assetsconfig.AssetsConfigRepository
import com.myrran.infraestructure.learned.LearnedRepository
import com.myrran.infraestructure.skill.SkillAdapter
import com.myrran.infraestructure.skill.SkillRepository
import com.myrran.infraestructure.skilltemplate.SkillTemplateAdapter
import com.myrran.infraestructure.skilltemplate.SkillTemplateRepository
import com.myrran.view.atlas.Atlas
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.skills.SkillViewFactory
import com.myrran.view.ui.skills.assets.SkillViewAssets
import ktx.app.KtxScreen

class MainScreen(

    private val inputMultiplexer: InputMultiplexer = InputMultiplexer(),
    private val batch: SpriteBatch = SpriteBatch(),
    private val uiStage: Stage = Stage(),
    private val atlas: Atlas = Atlas(
        assetManager = AssetManager()),

    private val assetsConfigRepository: AssetsConfigRepository = AssetsConfigRepository(DeSerializer()),

): KtxScreen
{
    private val skillTemplateRepository: SkillTemplateRepository
    private val skillRepository: SkillRepository
    private val learnedRepository: LearnedRepository
    private val playerSkillBook: PlayerSkillBook

    private val fpsText: TextView<String>

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        inputMultiplexer.addProcessor(uiStage)
        Gdx.input.inputProcessor = inputMultiplexer

        val initialAssets = assetsConfigRepository.loadAssetCollection("UIAssets.json")
        atlas.load(initialAssets)
        atlas.finishLoading()

        val deSerializer = DeSerializer()
        val skillTemplateAdapter = SkillTemplateAdapter()
        skillTemplateRepository = SkillTemplateRepository(skillTemplateAdapter, deSerializer)

        val skillAdapter = SkillAdapter()
        skillRepository = SkillRepository(skillAdapter, deSerializer)
        learnedRepository = LearnedRepository(deSerializer)
        playerSkillBook = PlayerSkillBook(skillTemplateRepository, learnedRepository, skillRepository)

        fpsText = TextView("FPS: ?", atlas.getFont("20.fnt"), shadowTickness = 2f, formater = {it})
        uiStage.addActor(fpsText)

        val fireTemplate = skillTemplateRepository.findBy( BuffSkillTemplateId("FIRE_1") )!!
        //playerSkillBook.addSubSkillTo(SkillId.from("95a1bfb2-a2bd-47d3-920b-e7f9ad798b76"), SubSkillSlotId("IMPACT"), SubSkillTemplateId("EXPLOSION_1"))
        //val controller = SkillController(skill.id, playerSkillBook)
        val controller = SkillController(SkillId.from("95a1bfb2-a2bd-47d3-920b-e7f9ad798b76"), playerSkillBook)


        val assets = SkillViewAssets(
            skillIcon = atlas.getTextureRegion("Atlas.atlas", "TexturasIconos/FireBall"),
            tableBackgroundLightToDark = atlas.getNinePatchDrawable("Atlas.atlas", "TexturasIconos/NineLightToDark", Color.WHITE, 1f),
            tableBackgroundLight = atlas.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineLight", Color.WHITE, 0.90f),
            tableBackgroundDark = atlas.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineDark", Color.WHITE, 0.90f),
            font20 = atlas.getFont("20.fnt"),
            font14 = atlas.getFont("14.fnt"),
            font12 = atlas.getFont("14.fnt"),
            font10 =  atlas.getFont("14.fnt"),
            statBarBack = atlas.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalentoFondo"),
            statBarFront = atlas.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalento"),
        )

        val dragAndDropManager = DragAndDropManager(DaD())
        val skillViewFactory = SkillViewFactory(dragAndDropManager, assets)

        val skill = playerSkillBook.createdSkillsRepository.findBy(SkillId.from("95a1bfb2-a2bd-47d3-920b-e7f9ad798b76"))!!
        val skillView = skillViewFactory.createSkillView(skill, controller)
        uiStage.addActor(skillView)
        skillView.setPosition(200f, 100f)

        val templateView = skillViewFactory.createBuffTemplateView(fireTemplate)
        uiStage.addActor(templateView)
        templateView.setPosition(50f, 50f)



        //skillView.setDebug(true, true)
        //templateView.setDebug(true, true)
        //uiStage.setDebugUnderMouse(true)
        //uiStage.setDebugAll(true)

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

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.10f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun resize(width: Int, height: Int) {

        uiStage.viewport.update(Gdx.graphics.width, Gdx.graphics.height)
    }

    override fun dispose() {

        batch.dispose()
        uiStage.dispose()
        atlas.dispose()
    }
}
