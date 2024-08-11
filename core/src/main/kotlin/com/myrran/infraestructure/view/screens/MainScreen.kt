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
import com.myrran.infraestructure.input.PlayerInputListener
import com.myrran.infraestructure.input.PlayerInputs
import com.myrran.infraestructure.repositories.assetsconfig.AssetsConfigRepository
import com.myrran.infraestructure.view.mob.player.PlayerViewAssets
import com.myrran.infraestructure.view.mob.player.PlayerViewFactory
import com.myrran.infraestructure.view.world.UIView
import com.myrran.infraestructure.view.world.WorldView
import ktx.app.KtxScreen

class MainScreen(

    private val batch: SpriteBatch = SpriteBatch(),
    private val assetStorage: AssetStorage = AssetStorage(
        assetManager = AssetManager()),

    private val assetsConfigRepository: AssetsConfigRepository = AssetsConfigRepository(DeSerializer()),

): KtxScreen
{

    private val model: World
    private val worldView: WorldView
    private val uiView: UIView

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        val initialAssets = assetsConfigRepository.loadAssetCollection("UIAssets.json")
        assetStorage.load(initialAssets)
        assetStorage.finishLoading()

        val playerAssets = PlayerViewAssets(
            characterTexture = assetStorage.getTextureRegion("Atlas.atlas", "BAK/Player Sprites/Player"))

        val playerViewFactory = PlayerViewFactory(playerAssets)
        val playerInputs = PlayerInputs()

        model = World(playerInputs)
        worldView = WorldView(model, playerInputs, playerViewFactory)
        uiView = UIView(Stage(), assetStorage)

        val playerInputListener = PlayerInputListener(playerInputs, worldView.camera)
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(playerInputListener)
        inputMultiplexer.addProcessor(worldView.worldStage)
        inputMultiplexer.addProcessor(uiView.uiStage)
        Gdx.input.inputProcessor = inputMultiplexer
    }

    // RENDER:
    //--------------------------------------------------------------------------------------------------------

    private var timeStep: Float = 0f
    private val fixedTimestep: Float = 0.015f

    override fun render(delta: Float) {

        clearScreen()

        batch.begin()
        batch.end()

        timeStep += delta

        if (timeStep >= fixedTimestep) {

            model.saveLastPosition()

            while (timeStep >= fixedTimestep) {

                model.update(fixedTimestep)

                timeStep -= fixedTimestep
            }
        }

        worldView.render(delta, timeStep / fixedTimestep)

        uiView.render(delta)
    }

    private fun clearScreen() {

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }

    override fun resize(width: Int, height: Int) {

        uiView.resize(width, height)
    }

    override fun dispose() {

        batch.dispose()
        assetStorage.dispose()
        model.dispose()
        worldView.dispose()
        uiView.dispose()
    }
}
