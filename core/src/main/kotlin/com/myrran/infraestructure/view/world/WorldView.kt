package com.myrran.infraestructure.view.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.events.WorldEvent
import com.myrran.domain.misc.observer.Observer
import com.myrran.domain.mob.metrics.Pixel
import com.myrran.domain.world.World
import com.myrran.infraestructure.input.PlayerInputs
import com.myrran.infraestructure.view.mob.PlayerView
import com.myrran.infraestructure.view.mob.player.PlayerViewFactory

class WorldView(

    private val model: World,
    private val playerInputs: PlayerInputs,
    private val playerViewFactory: PlayerViewFactory,
    val worldStage: Stage = Stage(),
    val camera: OrthographicCamera = OrthographicCamera(
        Pixel(Gdx.graphics.width).toMeters().toFloat(),
        Pixel(Gdx.graphics.height).toMeters().toFloat()),
    private val box2dDebug: Box2DDebugRenderer = Box2DDebugRenderer()

): Observer<WorldEvent>, Disposable
{
    private val playerView: PlayerView = playerViewFactory.toPlayerView(model.player)
    //private val cameraTarget: Location<Vector2> = playerView

    init {

        worldStage.addActor(playerView)
        camera.zoom = 0.5f
        worldStage.viewport.camera = camera
    }

    fun render(deltaTime: Float, fractionOfTimestep: Float) {

        box2dDebug.render(model.box2dWorld, camera.combined)

        interpolatePositions(fractionOfTimestep)
        //camera.position.set(playerView.x, playerView.y, 0f)
        camera.update()

        worldStage.act(deltaTime)
        worldStage.draw()
    }

    private fun interpolatePositions(fractionOfTimestep: Float) {

        playerView.update(fractionOfTimestep)
    }

    override fun propertyChange(event: WorldEvent) {


    }

    override fun dispose() {

        worldStage.dispose()
        box2dDebug.dispose()
    }
}
