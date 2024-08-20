package com.myrran.infraestructure.view

import box2dLight.RayHandler
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.mob.spells.spell.SpellBolt
import com.myrran.domain.events.Event
import com.myrran.domain.events.MobCreatedEvent
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.events.SpellCreatedEvent
import com.myrran.domain.misc.metrics.PositionPixels
import com.myrran.domain.world.World
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.eventbus.EventListener
import com.myrran.infraestructure.eventbus.EventSender
import com.myrran.infraestructure.view.common.Camera
import com.myrran.infraestructure.view.mobs.common.MobView
import com.myrran.infraestructure.view.mobs.common.MobViewFactory
import com.myrran.infraestructure.view.mobs.player.PlayerView

class WorldView(

    private val model: World,
    private val stage: Stage,
    private val camera: Camera,
    private val mobViewFactory: MobViewFactory,
    private val rayHandler: RayHandler,
    private val eventDispatcher: EventDispatcher,

): EventSender by eventDispatcher, EventListener, Disposable
{
    private val playerView: PlayerView = mobViewFactory.createPlayer(model.player)
    private val spellViews: MutableMap<EntityId, MobView> = mutableMapOf()
    private val box2dDebug: Box2DDebugRenderer = Box2DDebugRenderer()
    //private val cameraTarget: Location<Vector2> = playerView

    init {

        //box2dDebug.isDrawVelocities = true
        rayHandler.setAmbientLight(0.8f)
        stage.addActor(playerView)

        //camera.zoom(0.25f)
        camera.assignCameraToStage(stage)
        addListener(this, SpellCreatedEvent::class, MobRemovedEvent::class)
    }

    fun render(deltaTime: Float, fractionOfTimestep: Float) {

        box2dDebug.render(model.worldBox2D, camera.cameraBox2D.combined)

        updatePositionUsingInterpolation(fractionOfTimestep)
        updatePlayerWithTheirTargets()

        //camera.setPosition(PositionPixels(playerView.x, playerView.y))
        camera.update()
        camera.updateRayHandler(rayHandler)

        rayHandler.updateAndRender()

        stage.act(deltaTime)
        stage.draw()
    }

    // EVENTS:
    //--------------------------------------------------------------------------------------------------------

    override fun dispose() {

        spellViews.values.forEach { it.dispose() }
        playerView.dispose()
        stage.dispose()
        box2dDebug.dispose()
        rayHandler.dispose()
        removeListener( this)
    }

    override fun handleEvent(event: Event) {

        when (event) {
            is MobCreatedEvent -> Unit
            is MobRemovedEvent -> removeMob(event)
            is SpellCreatedEvent -> createSpell(event)
            else -> Unit
        }
    }

    // MISC:
    //--------------------------------------------------------------------------------------------------------

    private fun updatePositionUsingInterpolation(fractionOfTimestep: Float) {

        playerView.updatePosition(fractionOfTimestep)
        spellViews.values.forEach { it.updatePosition(fractionOfTimestep) }
    }

    private fun updatePlayerWithTheirTargets() =

        camera.toWorldCoordinates(PositionPixels(Gdx.input.x, Gdx.input.y))
            .also { model.player.updateTarget(it) }

    private fun createSpell(event: SpellCreatedEvent) =

        mobViewFactory.createSpell(event.spell)
            .also { spellViews[it.id] = it }
            .also { stage.addActor(it as Actor) }

    private fun removeMob(event: MobRemovedEvent) {

        when (event.mob) {

            is SpellBolt -> spellViews.remove(event.mob.id)?.also { it.dispose() }
            else -> Unit
        }
    }
}
