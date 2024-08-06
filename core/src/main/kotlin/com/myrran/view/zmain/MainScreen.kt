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
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.spells.SpellBook
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
import com.myrran.view.ui.skills.SkillViewId
import com.myrran.view.ui.skills.assets.SkillViewAssets
import com.myrran.view.ui.skills.book.BuffSkillTemplateViews
import com.myrran.view.ui.skills.book.SubSkillTemplateViews
import ktx.app.KtxScreen
import java.util.UUID

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
    private val spellBook: SpellBook

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
        spellBook = SpellBook(skillTemplateRepository, learnedRepository, skillRepository)

        fpsText = TextView("FPS: ?", atlas.getFont("20.fnt"), shadowTickness = 2f, formater = {it})
        uiStage.addActor(fpsText)

        //playerSkillBook.addSubSkillTo(SkillId.from("95a1bfb2-a2bd-47d3-920b-e7f9ad798b76"), SubSkillSlotId("IMPACT"), SubSkillTemplateId("EXPLOSION_1"))
        //val controller = SkillController(skill.id, playerSkillBook)
        val controller = SkillController(SkillId.from("95a1bfb2-a2bd-47d3-920b-e7f9ad798b76"), spellBook)


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

        val dragAndDropManager = DragAndDropManager(DaD(), DaD())
        val skillViewFactory = SkillViewFactory(dragAndDropManager, assets)

        val skill = spellBook.createdSkillsRepository.findBy(SkillId.from("95a1bfb2-a2bd-47d3-920b-e7f9ad798b76"))!!
        val skillView = skillViewFactory.createSkillView(skill, controller)
        uiStage.addActor(skillView)
        skillView.setPosition(550f, 200f)

        val buffList = BuffSkillTemplateViews(SkillViewId(UUID.randomUUID()), spellBook, assets, skillViewFactory)
        uiStage.addActor(buffList)
        buffList.setPosition(0f, 157f)

        val subList = SubSkillTemplateViews(SkillViewId(UUID.randomUUID()), spellBook, assets, skillViewFactory)
        uiStage.addActor(subList)
        subList.setPosition(264f, 157f)

        //playerSkillBook.learn(BuffSkillTemplateId("BOMB_1"))
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
        batch.end()

        uiStage.act()
        renderUI(delta)
        uiStage.draw()
    }

    private fun renderUI(delta: Float) {

        fpsText.setText(Gdx.graphics.framesPerSecond)
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
