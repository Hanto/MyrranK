package com.myrran.infraestructure.view.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.myrran.application.World
import com.myrran.domain.misc.DeSerializer
import com.myrran.domain.mob.BodyFactory
import com.myrran.domain.mob.MobFactory
import com.myrran.domain.mob.metrics.Pixel
import com.myrran.infraestructure.assets.AssetStorage
import com.myrran.infraestructure.input.PlayerController
import com.myrran.infraestructure.input.PlayerInputs
import com.myrran.infraestructure.repositories.assetsconfig.AssetsConfigRepository
import com.myrran.infraestructure.view.mob.MobViewFactory
import com.myrran.infraestructure.view.mob.PlayerViewAssets
import com.myrran.infraestructure.view.world.UIView
import com.myrran.infraestructure.view.world.View
import com.myrran.infraestructure.view.world.WorldView
import ktx.app.KtxScreen
import com.badlogic.gdx.physics.box2d.World as Box2DWorld

class MainScreen(

    private val assetStorage: AssetStorage = AssetStorage(
        assetManager = AssetManager()),

    private val assetsConfigRepository: AssetsConfigRepository = AssetsConfigRepository(DeSerializer()),


): KtxScreen
{
    private val world: World
    private val view: View
    private val playerController: PlayerController

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        // ASSETS:
        //----------------------------------------------------------------------------------------------------

        val initialAssets = assetsConfigRepository.loadAssetCollection("UIAssets.json")
        assetStorage.load(initialAssets)
        assetStorage.finishLoading()

        // WORLD:
        //----------------------------------------------------------------------------------------------------

        val box2dWorld = Box2DWorld(Vector2(0f, 0f), true)
        val bodyFactory = BodyFactory(box2dWorld)
        val mobFactory = MobFactory(bodyFactory)
        val player = mobFactory.createPlayer()

        world = World(
            player = player,
            box2dWorld = box2dWorld,
            mobFactory = mobFactory)

        // WORLD VIEW:
        //----------------------------------------------------------------------------------------------------

        val worldStage = Stage()
        val playerAssets = PlayerViewAssets(assetStorage.getTextureRegion("Atlas.atlas", "BAK/Player Sprites/Player"))
        val mobViewFactory = MobViewFactory(playerAssets)
        val worldCamera = OrthographicCamera(
            Pixel(Gdx.graphics.width).toMeters().toFloat(),
            Pixel(Gdx.graphics.height).toMeters().toFloat())

        val worldView = WorldView(
            model = world,
            stage = worldStage,
            camera = worldCamera,
            mobViewFactory = mobViewFactory)

        // UI VIEW:
        //----------------------------------------------------------------------------------------------------

        val uiStage = Stage()

        val uiView = UIView(
            stage = uiStage,
            assetStorage = assetStorage)

        // VIEW:
        //----------------------------------------------------------------------------------------------------

        view = View(
            worldView = worldView,
            uiView = uiView,
            batch = SpriteBatch())

        // CONTROLLER:
        //----------------------------------------------------------------------------------------------------

        val playerInputs = PlayerInputs()

        playerController = PlayerController(
            player = player,
            worldCamera = worldCamera,
            playerInputs = playerInputs)

        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(playerController)
        inputMultiplexer.addProcessor(uiStage)
        inputMultiplexer.addProcessor(worldStage)
        Gdx.input.inputProcessor = inputMultiplexer
    }

    // RENDER:
    //--------------------------------------------------------------------------------------------------------

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
