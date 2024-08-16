package com.myrran.infraestructure.view.screens

import box2dLight.RayHandler
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
import com.badlogic.gdx.scenes.scene2d.Stage
import com.myrran.badlogic.DaD
import com.myrran.domain.World
import com.myrran.domain.events.EnemyCreatedEvent
import com.myrran.domain.misc.DeSerializer
import com.myrran.domain.mobs.common.MobFactory
import com.myrran.domain.mobs.common.metrics.Pixel
import com.myrran.domain.mobs.common.metrics.PositionPixels
import com.myrran.domain.mobs.common.steerable.BodyFactory
import com.myrran.domain.mobs.common.steerable.SteeringBehaviorsFactory
import com.myrran.domain.mobs.spells.spell.WorldBox2D
import com.myrran.domain.skills.LearnedTemplates
import com.myrran.domain.skills.SpellBook
import com.myrran.infraestructure.assets.AssetStorage
import com.myrran.infraestructure.controller.player.PlayerController
import com.myrran.infraestructure.controller.player.PlayerInputs
import com.myrran.infraestructure.controller.skills.DragAndDropManager
import com.myrran.infraestructure.controller.skills.SpellBookController
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.repositories.assetsconfig.AssetsConfigRepository
import com.myrran.infraestructure.repositories.learnedskilltemplate.LearnedSkillTemplateRepository
import com.myrran.infraestructure.repositories.skill.SkillAdapter
import com.myrran.infraestructure.repositories.skill.SkillRepository
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateAdapter
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateRepository
import com.myrran.infraestructure.view.UIView
import com.myrran.infraestructure.view.View
import com.myrran.infraestructure.view.WorldView
import com.myrran.infraestructure.view.common.Camera
import com.myrran.infraestructure.view.mobs.common.MobViewFactory
import com.myrran.infraestructure.view.mobs.player.PlayerViewAssets
import com.myrran.infraestructure.view.mobs.spells.SpellViewAssets
import com.myrran.infraestructure.view.skills.SkillViewAssets
import com.myrran.infraestructure.view.skills.SkillViewFactory
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

        // WORLD:
        //----------------------------------------------------------------------------------------------------

        val worldBox2D = WorldBox2D(Vector2(0f, 0f), true)
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
        val playerInputs = PlayerInputs()
        val bodyFactory = BodyFactory()
        val mobFactory = MobFactory(
            bodyFactory = bodyFactory,
            eventDispatcher = eventDispatcher,
            worldBox2D = worldBox2D,
            playerInputs = playerInputs)
        val player = mobFactory.createPlayer()
        player.changeSelecctedSkillTo(spellBook.findAllPlayerSkills().first())
        val world = World(
            player = player,
            spellBook = spellBook,
            worldBox2D = worldBox2D,
            mobFactory = mobFactory,
            eventDispatcher = eventDispatcher)

        // UI VIEW:
        //----------------------------------------------------------------------------------------------------

        val uiStage = Stage()
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
        val bookController = SpellBookController(book = spellBook)
        val dragAndDropManager = DragAndDropManager(effectDaDs = DaD(), formDaDs = DaD())
        val skillViewFactory = SkillViewFactory(dragAndDropManager =  dragAndDropManager, assets = skillViewAssets)
        val uiView = UIView(
            spellBook = spellBook,
            stage = uiStage,
            assets = skillViewAssets,
            bookController = bookController,
            skillViewFactory = skillViewFactory)

        // WORLD VIEW:
        //----------------------------------------------------------------------------------------------------

        val worldStage = Stage()
        val rayHandler = RayHandler(worldBox2D)
        val playerAssets = PlayerViewAssets(
            character =  assetStorage.getTextureRegion("Atlas.atlas", "BAK/Player Sprites/Player"),
            shadow = assetStorage.getTextureRegion("Atlas.atlas", "BAK/Player Sprites/Sombra"),
            nameplateBackground = assetStorage.getTextureRegion("Atlas.atlas", "TexturasMisc/Nameplate"),
            nameplateForeground = assetStorage.getTextureRegion("Atlas.atlas", "TexturasMisc/NameplateFondo"))
        val spellAssets = SpellViewAssets(
            spellBolt = assetStorage.getTextureRegion("Atlas.atlas", "AnimacionesSpells/SpellBalls_01n"))
        val mobViewFactory = MobViewFactory(
            playerAssets = playerAssets,
            spellAssets = spellAssets,
            rayHandler = rayHandler)
        val worldCamera = OrthographicCamera(
            Pixel(Gdx.graphics.width).toFloat(),
            Pixel(Gdx.graphics.height).toFloat())
        val worldCameraBox2d = OrthographicCamera(
            Pixel(Gdx.graphics.width).toMeters().toFloat(),
            Pixel(Gdx.graphics.height).toMeters().toFloat())
        val camera = Camera(
            cameraPixel = worldCamera,
            cameraBox2D = worldCameraBox2d)
        val worldView = WorldView(
            model = world,
            stage = worldStage,
            camera = camera,
            mobViewFactory = mobViewFactory,
            rayHandler = rayHandler,
            eventDispatcher = eventDispatcher)

        // VIEW:
        //----------------------------------------------------------------------------------------------------

        val playerController = PlayerController(world = world)
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(uiStage)
        inputMultiplexer.addProcessor(worldStage)
        inputMultiplexer.addProcessor(playerController)
        Gdx.input.inputProcessor = inputMultiplexer
        val view = View(
            worldView = worldView,
            uiView = uiView,
            inputMultiplexer = inputMultiplexer,
            batch = SpriteBatch())

        addScreen(MainScreen(assetStorage, world, view))
        setScreen<MainScreen>()

        val enemy01 = mobFactory.createEnemy()
        enemy01.position = PositionPixels(-200, -200).toMeters().toBox2dUnits()
        eventDispatcher.sendEvent(EnemyCreatedEvent(enemy01))
        enemy01.steerable.steeringBehavior = SteeringBehaviorsFactory().pursueAndEvadeEnemies(enemy01, player)

        val enemy02 = mobFactory.createEnemy()
        enemy02.position = PositionPixels(200, -200).toMeters().toBox2dUnits()
        eventDispatcher.sendEvent(EnemyCreatedEvent(enemy02))
        enemy02.steerable.steeringBehavior = SteeringBehaviorsFactory().pursueAndEvadeEnemies(enemy02, player)

        val enemy03 = mobFactory.createEnemy()
        enemy03.position = PositionPixels(200, 200).toMeters().toBox2dUnits()
        eventDispatcher.sendEvent(EnemyCreatedEvent(enemy03))
        enemy03.steerable.steeringBehavior = SteeringBehaviorsFactory().pursueAndEvadeEnemies(enemy03, player)

        val enemy04 = mobFactory.createEnemy()
        enemy04.position = PositionPixels(200, 300).toMeters().toBox2dUnits()
        eventDispatcher.sendEvent(EnemyCreatedEvent(enemy04))
        enemy04.steerable.steeringBehavior = SteeringBehaviorsFactory().pursueAndEvadeEnemies(enemy04, player)
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
