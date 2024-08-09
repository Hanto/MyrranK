package com.myrran.infraestructure.view.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.myrran.application.LearnedSkillTemplates
import com.myrran.application.SpellBook
import com.myrran.badlogic.DaD
import com.myrran.domain.misc.DeSerializer
import com.myrran.infraestructure.assets.AssetStorage
import com.myrran.infraestructure.controller.BookSkillController
import com.myrran.infraestructure.controller.DragAndDropManager
import com.myrran.infraestructure.repositories.assetsconfig.AssetsConfigRepository
import com.myrran.infraestructure.repositories.learnedskilltemplate.LearnedSkillTemplateRepository
import com.myrran.infraestructure.repositories.skill.SkillAdapter
import com.myrran.infraestructure.repositories.skill.SkillRepository
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateAdapter
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateRepository
import com.myrran.infraestructure.view.player.Pixie
import com.myrran.infraestructure.view.player.PlayerAnimation
import com.myrran.infraestructure.view.player.PlayerFactory
import com.myrran.infraestructure.view.player.PlayerViewAssets
import com.myrran.infraestructure.view.ui.misc.TextView
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets
import com.myrran.infraestructure.view.ui.skills.SkillViewFactory
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.created.skill.SkillViews
import com.myrran.infraestructure.view.ui.skills.templates.effect.EffectTemplateViews
import com.myrran.infraestructure.view.ui.skills.templates.form.FormTemplateViews
import com.myrran.infraestructure.view.ui.skills.templates.skill.SkillTemplateViews
import ktx.app.KtxScreen
import java.util.UUID

class MainScreen(

    private val inputMultiplexer: InputMultiplexer = InputMultiplexer(),
    private val batch: SpriteBatch = SpriteBatch(),
    private val uiStage: Stage = Stage(),
    private val worldStage: Stage = Stage(),
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
    private val camera: OrthographicCamera
    private val playerView: Pixie<PlayerAnimation>

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

        fpsText = TextView("0", assetStorage.getFont("20.fnt"), shadowTickness = 2f, formater = { "FPS: $it" })
        uiStage.addActor(fpsText)

        val assets = SkillViewAssets(
            font20 = assetStorage.getFont("20.fnt"),
            font14 = assetStorage.getFont("14.fnt"),
            font12 = assetStorage.getFont("Arial12.fnt"),
            font10 =  assetStorage.getFont("Arial10.fnt"),
            statBarBack = assetStorage.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalentoFondo"),
            statBarFront = assetStorage.getTextureRegion("Atlas.atlas", "TexturasMisc/CasillaTalento"),
            skillHeader = assetStorage.getNinePatchDrawable("Atlas.atlas", "TexturasIconos/NineLightToDark", Color(0.7f, 0.7f, 0.7f, 1.0f), 0.90f),
            templateHeader = assetStorage.getNinePatchDrawable("Atlas.atlas", "TexturasIconos/NineLightToDark", Color(0.7f, 0.7f, 0.7f, 1.0f), 0.90f),
            containerHeader = assetStorage.getNinePatchDrawable("Atlas.atlas", "TexturasIconos/NineLightToDark", Color(0.6f, 0.6f, 0.6f, 1.0f), 0.90f),
            containerBackground = assetStorage.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineLight", Color.WHITE, 0.90f),
            tableBackground = assetStorage.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineLight", Color.WHITE, 0.90f),
            tooltipBackground = assetStorage.getNinePatchDrawable("Atlas.atlas","TexturasIconos/NineLight", Color.WHITE, 0.95f),
            iconTextures = mapOf(
                "SkillIcon" to assetStorage.getTextureRegion("Atlas.atlas", "TexturasIconos/FireBall"),
                "EffectIcon" to assetStorage.getTextureRegion("Atlas.atlas", "TexturasIconos/Editar"),
                "FormIcon" to assetStorage.getTextureRegion("Atlas.atlas", "TexturasIconos/Muros")
            )
        )

        val dragAndDropManager = DragAndDropManager(DaD(), DaD())
        val skillViewFactory = SkillViewFactory(dragAndDropManager, assets)
        val bookController = BookSkillController(spellBook)

        val effectList = EffectTemplateViews(SkillViewId(UUID.randomUUID()), spellBook, assets, skillViewFactory)
        uiStage.addActor(effectList)
        effectList.setPosition(533f, 154f)

        val formList = FormTemplateViews(SkillViewId(UUID.randomUUID()), spellBook, assets, skillViewFactory)
        uiStage.addActor(formList)
        formList.setPosition(268f, 154f)

        val skillTemplateList = SkillTemplateViews(SkillViewId(UUID.randomUUID()), spellBook, assets, bookController, skillViewFactory)
        uiStage.addActor(skillTemplateList)
        skillTemplateList.setPosition(3f, 154f)

        val skillList = SkillViews(SkillViewId(UUID.randomUUID()), spellBook, assets, bookController, skillViewFactory)
        uiStage.addActor(skillList)
        skillList.setPosition(860f, 154f)


        val playerAssets = PlayerViewAssets(
            characterTexture = assetStorage.getTextureRegion("Atlas.atlas", "BAK/Player Sprites/Player"))

        playerView = PlayerFactory().toPlayerView(playerAssets)
        playerView.setAnimation(PlayerAnimation.IDDLE)

        playerView.addAction(Actions.forever(Actions.sequence(
            Actions.scaleTo(2f, 2f, 2f, Interpolation.circleIn),
            Actions.scaleTo(0.5f, 0.5f, 2f, Interpolation.circleOut)
        )))

        worldStage.addActor(playerView)

        camera = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        worldStage.viewport.camera = camera


        camera.zoom = 0.5f
    }

    // RENDER:
    //--------------------------------------------------------------------------------------------------------

    override fun render(delta: Float) {

        clearScreen()

        camera.position.set(playerView.originX, playerView.originY, 0f)
        camera.update()

        batch.begin()
        batch.end()

        worldStage.act()
        worldStage.draw()

        uiStage.act()
        renderUI(delta)
        uiStage.draw()
    }

    private fun renderUI(delta: Float) {

        fpsText.setText(Gdx.graphics.framesPerSecond.toString())
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
