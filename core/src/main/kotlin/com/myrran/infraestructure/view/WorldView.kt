package com.myrran.infraestructure.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.World
import com.myrran.domain.events.Event
import com.myrran.domain.events.MobRemovedEvent
import com.myrran.domain.events.SpellCreatedEvent
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.metrics.PositionPixels
import com.myrran.domain.mobs.spells.spell.SpellBolt
import com.myrran.infraestructure.controller.player.PlayerController
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.eventbus.EventListener
import com.myrran.infraestructure.eventbus.EventSender
import com.myrran.infraestructure.view.mobs.common.MobViewFactory
import com.myrran.infraestructure.view.mobs.player.PlayerView
import com.myrran.infraestructure.view.mobs.spells.spell.SpellView

class WorldView(

    private val model: World,
    private val stage: Stage,
    private val camera: OrthographicCamera,
    private val mobViewFactory: MobViewFactory,
    private val playerController: PlayerController,
    private val eventDispatcher: EventDispatcher,

): EventSender by eventDispatcher, EventListener, Disposable
{
    private val playerView: PlayerView = mobViewFactory.createPlayer(model.player)
    private val spellViews: MutableMap<MobId, SpellView> = mutableMapOf()
    private val box2dDebug: Box2DDebugRenderer = Box2DDebugRenderer()
    //private val cameraTarget: Location<Vector2> = playerView

    init {

        stage.addActor(playerView)
        //camera.zoom = 0.5f
        stage.viewport.camera = camera
        addListener(listener = this, SpellCreatedEvent::class, MobRemovedEvent::class)
    }

    fun render(deltaTime: Float, fractionOfTimestep: Float) {

        box2dDebug.render(model.worldBox2D, camera.combined)

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
        removeListener(listener = this)
    }

    override fun handleEvent(event: Event) {

        when (event) {
            is SpellCreatedEvent -> createSpell(event)
            is MobRemovedEvent -> removeMob(event)
            else -> Unit
        }
    }

    // MISC:
    //--------------------------------------------------------------------------------------------------------

    private fun interpolatePositions(fractionOfTimestep: Float) {

        playerView.update(fractionOfTimestep)
        spellViews.values.forEach { it.update(fractionOfTimestep) }
    }

    private fun updatePlayerWithTheirTargets() {

        model.player.pointingAt = PositionPixels(Gdx.input.x, Gdx.input.y).toWorldPosition(camera)
    }

    private fun createSpell(event: SpellCreatedEvent) {

        val mob = mobViewFactory.createSpell(event.spell)
        spellViews[mob.id] = mob
        stage.addActor(mob as Actor)
    }

    private fun removeMob(event: MobRemovedEvent) {

        when (event.mob) {

            is SpellBolt -> spellViews.remove(event.mob.id)?.also { (it as Actor).remove() }
            else -> Unit
        }
    }
}
