package com.myrran.infraestructure.view.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.myrran.application.World
import com.myrran.domain.events.Event
import com.myrran.domain.events.WorldEvent.MobRemovedEvent
import com.myrran.domain.events.WorldEvent.PlayerSpellCastedEvent
import com.myrran.domain.mob.metrics.PositionPixels
import com.myrran.infraestructure.controller.PlayerController
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.eventbus.EventListener
import com.myrran.infraestructure.view.mob.MobViewFactory
import com.myrran.infraestructure.view.mob.player.PlayerView

class WorldView(

    private val model: World,
    private val stage: Stage,
    private val camera: OrthographicCamera,
    private val mobViewFactory: MobViewFactory,
    private val playerController: PlayerController,
    private val eventDispatcher: EventDispatcher,

): Disposable, EventListener
{
    private val playerView: PlayerView = mobViewFactory.createPlayer(model.player)
    private val box2dDebug: Box2DDebugRenderer = Box2DDebugRenderer()
    //private val cameraTarget: Location<Vector2> = playerView

    init {

        stage.addActor(playerView)
        //camera.zoom = 0.5f
        stage.viewport.camera = camera
        eventDispatcher.addListener(listener = this, PlayerSpellCastedEvent::class, MobRemovedEvent::class)
    }

    fun render(deltaTime: Float, fractionOfTimestep: Float) {

        box2dDebug.render(model.box2dWorld, camera.combined)

        interpolatePositions(fractionOfTimestep)
        updatePlayerWithTheirTargets()

        //camera.position.set(playerView.x, playerView.y, 0f)
        camera.update()

        stage.act(deltaTime)
        stage.draw()
    }

    // EVENTS:
    //--------------------------------------------------------------------------------------------------------

    override fun dispose() {

        stage.dispose()
        box2dDebug.dispose()
    }

    override fun handleEvent(event: Event) {

    }

    // MISC:
    //--------------------------------------------------------------------------------------------------------

    private fun interpolatePositions(fractionOfTimestep: Float) =

        playerView.update(fractionOfTimestep)


    private fun updatePlayerWithTheirTargets() {

        model.player.pointingAt = PositionPixels(Gdx.input.x, Gdx.input.y).toWorldPosition(camera)
    }

}
