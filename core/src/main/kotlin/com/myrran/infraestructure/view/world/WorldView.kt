package com.myrran.infraestructure.view.world

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.myrran.application.World
import com.myrran.domain.events.WorldEvent
import com.myrran.domain.misc.observer.Observer
import com.myrran.infraestructure.view.mob.MobViewFactory
import com.myrran.infraestructure.view.mob.PlayerView

class WorldView(

    private val model: World,
    private val stage: Stage,
    private val camera: OrthographicCamera,
    private val mobViewFactory: MobViewFactory

): Observer<WorldEvent>, Disposable
{
    private val playerView: PlayerView = mobViewFactory.createPlayer(model.player)
    private val box2dDebug: Box2DDebugRenderer = Box2DDebugRenderer()
    //private val cameraTarget: Location<Vector2> = playerView

    init {

        stage.addActor(playerView)
        camera.zoom = 0.5f
        stage.viewport.camera = camera
    }

    fun render(deltaTime: Float, fractionOfTimestep: Float) {

        box2dDebug.render(model.box2dWorld, camera.combined)

        interpolatePositions(fractionOfTimestep)
        //camera.position.set(playerView.x, playerView.y, 0f)
        camera.update()

        stage.act(deltaTime)
        stage.draw()
    }

    private fun interpolatePositions(fractionOfTimestep: Float) {

        playerView.update(fractionOfTimestep)
    }

    override fun propertyChange(event: WorldEvent) {


    }

    override fun dispose() {

        stage.dispose()
        box2dDebug.dispose()
    }
}
