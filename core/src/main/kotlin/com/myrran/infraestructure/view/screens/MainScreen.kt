package com.myrran.infraestructure.view.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.myrran.domain.misc.DeSerializer
import com.myrran.domain.world.World
import com.myrran.infraestructure.assets.AssetStorage
import com.myrran.infraestructure.input.PlayerController
import com.myrran.infraestructure.input.PlayerInputs
import com.myrran.infraestructure.repositories.assetsconfig.AssetsConfigRepository
import com.myrran.infraestructure.view.mob.player.PlayerViewAssets
import com.myrran.infraestructure.view.mob.player.PlayerViewFactory
import com.myrran.infraestructure.view.world.UIView
import com.myrran.infraestructure.view.world.View
import com.myrran.infraestructure.view.world.WorldView
import ktx.app.KtxScreen

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

        val initialAssets = assetsConfigRepository.loadAssetCollection("UIAssets.json")
        assetStorage.load(initialAssets)
        assetStorage.finishLoading()

        val playerAssets = PlayerViewAssets(assetStorage.getTextureRegion("Atlas.atlas", "BAK/Player Sprites/Player"))

        val playerViewFactory = PlayerViewFactory(playerAssets)
        val playerInputs = PlayerInputs()

        world = World(playerInputs)
        val worldView = WorldView(world, playerViewFactory)
        val uiView = UIView(Stage(), assetStorage)
        view = View(worldView, uiView, SpriteBatch())

        playerController = PlayerController(playerInputs, worldView.camera)
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(playerController)
        inputMultiplexer.addProcessor(worldView.stage)
        inputMultiplexer.addProcessor(uiView.stage)
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
