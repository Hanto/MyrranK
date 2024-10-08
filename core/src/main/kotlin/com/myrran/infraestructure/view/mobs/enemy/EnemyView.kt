package com.myrran.infraestructure.view.mobs.enemy

import box2dLight.ConeLight
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.mob.enemy.Enemy
import com.myrran.domain.events.EffectAddedEvent
import com.myrran.domain.events.EffectRemovedEvent
import com.myrran.domain.events.EffectTickedEvent
import com.myrran.domain.events.Event
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.metrics.Degree
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.misc.metrics.Radian
import com.myrran.infraestructure.eventbus.EventDispatcher
import com.myrran.infraestructure.eventbus.EventListener
import com.myrran.infraestructure.view.mobs.common.EffectsView
import com.myrran.infraestructure.view.mobs.common.HealthBar
import com.myrran.infraestructure.view.mobs.common.MobView
import com.myrran.infraestructure.view.mobs.common.SpriteAnimated
import com.myrran.infraestructure.view.mobs.common.SpriteStatic
import com.myrran.infraestructure.view.mobs.player.EnemyAnimation

class EnemyView(

    private val model: Enemy,
    private val character: SpriteAnimated<EnemyAnimation>,
    private val shadow: SpriteStatic,
    private val healthBar: HealthBar,
    private val effectsView: EffectsView,
    private val lineOfSightLight: ConeLight,

    private val eventDispatcher: EventDispatcher

): Group(), MobView, Identifiable<EntityId>, Disposable, EventListener
{
    override val id: EntityId = model.id

    // INIT:
    //--------------------------------------------------------------------------------------------------------

    init {

        addActor(shadow)
        addActor(character)
        addActor(healthBar)
        addActor(effectsView)
        setSize(character.width, character.height)
        setOrigin(character.width/2, character.height/2)
        shadow.moveBy(0f, -5f)
        healthBar.moveBy(-2f, 40f)
        effectsView.moveBy(character.width +8, character.height)

        eventDispatcher.addListener(this, EffectTickedEvent::class, EffectAddedEvent::class, EffectRemovedEvent::class)
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun updatePosition(fractionOfTimestep: Float) {

        model.getInterpolatedPosition(fractionOfTimestep)
            .let { PositionMeters(it.x, it.y).toPixels() }
            .also { setPosition(it.x.toFloat(), it.y.toFloat(), Align.center) }
    }

    override fun act(deltaTime: Float) {

        setWalkingAnimation()

        lineOfSightLight.direction = model.orientation
        healthBar.update()

        super.act(deltaTime)
    }

    override fun dispose() {

        lineOfSightLight.remove()
        eventDispatcher.removeListener(this)
    }

    override fun handleEvent(event: Event) =

        when (event) {
            is EffectAddedEvent -> if (event.effectableId == model.id) effectsView.update() else Unit
            is EffectRemovedEvent -> if (event.effectableId == model.id) effectsView.update() else Unit
            is EffectTickedEvent -> if (event.effectableId == model.id) effectsView.update(event.effectId) else Unit
            else -> Unit
        }

    // ANIMATION:
    //--------------------------------------------------------------------------------------------------------

    private fun setWalkingAnimation() {

        val orientation = Radian(model.orientation).toDegrees().normalize()
        val animation = when {

            Degree(45f) < orientation && orientation < Degree(135f) -> EnemyAnimation.WALK_NORTH
            Degree(225f) < orientation && orientation < Degree(315f) -> EnemyAnimation.WALK_SOUTH
            Degree(135f) < orientation && orientation < Degree(225f) -> EnemyAnimation.WALK_WEST
            Degree(0f) <= orientation && orientation < Degree(45f)  ||
                Degree(0f) > orientation && orientation >= Degree(-45f) -> EnemyAnimation.WALK_EAST
            else -> EnemyAnimation.WALK_EAST
        }

        character.setAnimation(animation)
    }
}
