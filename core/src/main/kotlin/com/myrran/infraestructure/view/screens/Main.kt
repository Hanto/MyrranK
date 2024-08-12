package com.myrran.infraestructure.view.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.ai.msg.MessageDispatcher
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.scenes.scene2d.Stage
import com.myrran.application.LearnedTemplates
import com.myrran.application.SpellBook
import com.myrran.badlogic.DaD
import com.myrran.domain.misc.DeSerializer
import com.myrran.domain.mob.BodyFactory
import com.myrran.domain.mob.MobFactory
import com.myrran.domain.mob.metrics.Pixel
import com.myrran.infraestructure.assets.AssetStorage
import com.myrran.infraestructure.controller.DragAndDropManager
import com.myrran.infraestructure.controller.PlayerController
import com.myrran.infraestructure.controller.PlayerInputs
import com.myrran.infraestructure.controller.SpellBookController
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.repositories.assetsconfig.AssetsConfigRepository
import com.myrran.infraestructure.repositories.learnedskilltemplate.LearnedSkillTemplateRepository
import com.myrran.infraestructure.repositories.skill.SkillAdapter
import com.myrran.infraestructure.repositories.skill.SkillRepository
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateAdapter
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateRepository
import com.myrran.infraestructure.view.mob.MobViewFactory
import com.myrran.infraestructure.view.mob.player.PlayerViewAssets
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets
import com.myrran.infraestructure.view.ui.skills.SkillViewFactory
import com.myrran.infraestructure.view.world.UIView
import com.myrran.infraestructure.view.world.View
import com.myrran.infraestructure.view.world.WorldView
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.app.clearScreen
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.async.KtxAsync
import ktx.graphics.use

class Main : KtxGame<KtxScreen>() {

    override fun create() {

        KtxAsync.initiate()

        val messageDispatcher = MessageDispatcher()
        val eventDispatcher = EventDispatcher(
            messageDispatcher = messageDispatcher)

        // ASSETS:
        //----------------------------------------------------------------------------------------------------

        val deSerializer = DeSerializer()
        val assetStorage = AssetStorage(
            assetManager = AssetManager())
        val assetsConfigRepository = AssetsConfigRepository(
            deSerializer = deSerializer)
        val initialAssets = assetsConfigRepository.loadAssetCollection("UIAssets.json")
        assetStorage.load(initialAssets)
        assetStorage.finishLoading()

        // UI VIEW:
        //----------------------------------------------------------------------------------------------------

        val uiStage = Stage()
        val skillAdapter = SkillAdapter()
        val skillRepository = SkillRepository(
            skillAdapter = skillAdapter,
            deSerializer = deSerializer)
        val learnedRepository = LearnedSkillTemplateRepository(
            deSerializer = deSerializer)
        val skillTemplateAdapter = SkillTemplateAdapter()
        val skillTemplateRepository = SkillTemplateRepository(
            skillTemplateAdapter = skillTemplateAdapter,
            deSerializer = deSerializer)
        val learnedTemplates = LearnedTemplates(
            learnedRepository = learnedRepository,
            templateRepository = skillTemplateRepository)
        val spellBook = SpellBook(
            created = skillRepository,
            learned = learnedTemplates)
        val skillViewAssets = SkillViewAssets(
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
                "FormIcon" to assetStorage.getTextureRegion("Atlas.atlas", "TexturasIconos/Muros")))
        val bookController = SpellBookController(spellBook)
        val dragAndDropManager = DragAndDropManager(DaD(), DaD())
        val skillViewFactory = SkillViewFactory(dragAndDropManager, skillViewAssets)
        val uiView = UIView(
            spellBook = spellBook,
            stage = uiStage,
            assets = skillViewAssets,
            bookController = bookController,
            skillViewFactory = skillViewFactory)

        // WORLD:
        //----------------------------------------------------------------------------------------------------

        val box2dWorld = World(Vector2(0f, 0f), true)
        val bodyFactory = BodyFactory(
            world = box2dWorld)
        val mobFactory = MobFactory(
            bodyFactory = bodyFactory,
            eventDispatcher = eventDispatcher)
        val player = mobFactory.createPlayer()
        val world = com.myrran.application.World(
            player = player,
            spellBook = spellBook,
            box2dWorld = box2dWorld,
            mobFactory = mobFactory,
            eventDispatcher = eventDispatcher)

        // WORLD VIEW:
        //----------------------------------------------------------------------------------------------------

        val worldStage = Stage()
        val playerAssets = PlayerViewAssets(
            characterTexture =  assetStorage.getTextureRegion("Atlas.atlas", "BAK/Player Sprites/Player"))
        val mobViewFactory = MobViewFactory(
            assets = playerAssets)
        val worldCamera = OrthographicCamera(
            Pixel(Gdx.graphics.width).toMeters().toFloat(),
            Pixel(Gdx.graphics.height).toMeters().toFloat())
        val playerInputs = PlayerInputs()
        val playerController = PlayerController(
            world = world,
            worldCamera = worldCamera,
            playerInputs = playerInputs)
        val worldView = WorldView(
            model = world,
            stage = worldStage,
            camera = worldCamera,
            mobViewFactory = mobViewFactory,
            playerController = playerController,
            eventDispatcher = eventDispatcher)

        // VIEW:
        //----------------------------------------------------------------------------------------------------

        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(playerController)
        inputMultiplexer.addProcessor(uiStage)
        inputMultiplexer.addProcessor(worldStage)
        Gdx.input.inputProcessor = inputMultiplexer
        val view = View(
            worldView = worldView,
            uiView = uiView,
            inputMultiplexer = inputMultiplexer,
            batch = SpriteBatch())

        addScreen(MainScreen(assetStorage, world, view))
        setScreen<MainScreen>()
    }
}

class FirstScreen : KtxScreen {

    private val image = Texture("logo.png".toInternalFile(), true).apply { setFilter(Linear, Linear) }
    private val batch = SpriteBatch()

    override fun render(delta: Float) {

        clearScreen(red = 0.7f, green = 0.7f, blue = 0.7f)
        batch.use { it.draw(image, 100f, 160f) }
    }

    override fun dispose() {

        image.disposeSafely()
        batch.disposeSafely()
    }
}
