package com.myrran.infraestructure.view.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.myrran.application.LearnedSkillTemplates
import com.myrran.application.SpellBook
import com.myrran.badlogic.DaD
import com.myrran.controller.BookSkillController
import com.myrran.controller.DragAndDropManager
import com.myrran.domain.misc.DeSerializer
import com.myrran.infraestructure.assets.AssetStorage
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.repositories.assetsconfig.AssetsConfigRepository
import com.myrran.infraestructure.repositories.learnedskilltemplate.LearnedSkillTemplateRepository
import com.myrran.infraestructure.repositories.skill.SkillAdapter
import com.myrran.infraestructure.repositories.skill.SkillRepository
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateAdapter
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateRepository
import com.myrran.infraestructure.view.ui.misc.TextView
import com.myrran.infraestructure.view.ui.skills.SkillViewFactory
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.created.skill.SkillViews
import com.myrran.infraestructure.view.ui.skills.templates.buff.BuffTemplateViews
import com.myrran.infraestructure.view.ui.skills.templates.skill.SkillTemplateViews
import com.myrran.infraestructure.view.ui.skills.templates.subskill.SubSkillTemplateViews
import ktx.app.KtxScreen
import java.util.UUID

class MainScreen(

    private val inputMultiplexer: InputMultiplexer = InputMultiplexer(),
    private val batch: SpriteBatch = SpriteBatch(),
    private val uiStage: Stage = Stage(),
    private val assetStorage: AssetStorage = AssetStorage(
        assetManager = AssetManager()),

    private val assetsConfigRepository: AssetsConfigRepository = AssetsConfigRepository(DeSerializer()),

): KtxScreen
{
    private val skillTemplateRepository: SkillTemplateRepository
    private val skillRepository: SkillRepository
    private val learnedRepository: LearnedSkillTemplateRepository
    private val learnedTemplates: LearnedSkillTemplates
    private val spellBook: SpellBook

    private val fpsText: TextView<String>

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        inputMultiplexer.addProcessor(uiStage)
        Gdx.input.inputProcessor = inputMultiplexer

        val initialAssets = assetsConfigRepository.loadAssetCollection("UIAssets.json")
        assetStorage.load(initialAssets)
        assetStorage.finishLoading()

        val deSerializer = DeSerializer()
        val skillAdapter = SkillAdapter()
        skillRepository = SkillRepository(skillAdapter, deSerializer)
        learnedRepository = LearnedSkillTemplateRepository(deSerializer)
        val skillTemplateAdapter = SkillTemplateAdapter()
        skillTemplateRepository = SkillTemplateRepository(skillTemplateAdapter, deSerializer)
        learnedTemplates = LearnedSkillTemplates(learnedRepository, skillTemplateRepository)
        spellBook = SpellBook(skillRepository, learnedTemplates)

        fpsText = TextView("FPS: ?", assetStorage.getFont("20.fnt"), shadowTickness = 2f, formater = {it})
        uiStage.addActor(fpsText)

        //playerSkillBook.addSubSkillTo(SkillId.from("95a1bfb2-a2bd-47d3-920b-e7f9ad798b76"), SubSkillSlotId("IMPACT"), SubSkillTemplateId("EXPLOSION_1"))
        //val controller = SkillController(skill.id, playerSkillBook)

        val assets = SkillViewAssets(
            skillIcon = assetStorage.getTextureRegion("Atlas.atlas", "TexturasIconos/FireBall"),
            tableBackgroundLightToDark = assetStorage.getNinePatchDrawable("Atlas.atlas", "TexturasIconos/NineLightToDark", Color.WHITE, 1f),
            tableBackgroundLight = assetStorage.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineLight", Color.WHITE, 0.90f),
            tableBackgroundDark = assetStorage.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineDark", Color.WHITE, 0.90f),
            font20 = assetStorage.getFont("20.fnt"),
            font14 = assetStorage.getFont("14.fnt"),
            font12 = assetStorage.getFont("14.fnt"),
            font10 =  assetStorage.getFont("14.fnt"),
            statBarBack = assetStorage.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalentoFondo"),
            statBarFront = assetStorage.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalento"),
        )

        val dragAndDropManager = DragAndDropManager(DaD(), DaD())
        val skillViewFactory = SkillViewFactory(dragAndDropManager, assets)

        /*
        val skill = spellBook.created.findBy(SkillId.from("95a1bfb2-a2bd-47d3-920b-e7f9ad798b76"))!!
        val skillView = skillViewFactory.createSkillView(skill, controller)
        uiStage.addActor(skillView)
        skillView.setPosition(550f, 100f)
        */

        val bookController = BookSkillController(spellBook)

        val buffList = BuffTemplateViews(SkillViewId(UUID.randomUUID()), spellBook, assets, skillViewFactory)
        uiStage.addActor(buffList)
        buffList.setPosition(533f, 154f)

        val subList = SubSkillTemplateViews(SkillViewId(UUID.randomUUID()), spellBook, assets, skillViewFactory)
        uiStage.addActor(subList)
        subList.setPosition(268f, 154f)

        val skillTemplateList = SkillTemplateViews(SkillViewId(UUID.randomUUID()), spellBook, assets, bookController, skillViewFactory)
        uiStage.addActor(skillTemplateList)
        skillTemplateList.setPosition(3f, 154f)

        val skillList = SkillViews(SkillViewId(UUID.randomUUID()), spellBook, assets, bookController, skillViewFactory)
        uiStage.addActor(skillList)
        skillList.setPosition(860f, 154f)

        //spellBook.removeSkill(SkillId.from("3e4d0937-2a1a-45cc-a793-649130461dc0"))
        //spellBook.addSkill(SkillTemplateId("BOLT_1"))

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

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun resize(width: Int, height: Int) {

        uiStage.viewport.update(Gdx.graphics.width, Gdx.graphics.height)
    }

    override fun dispose() {

        batch.dispose()
        uiStage.dispose()
        assetStorage.dispose()
    }
}
