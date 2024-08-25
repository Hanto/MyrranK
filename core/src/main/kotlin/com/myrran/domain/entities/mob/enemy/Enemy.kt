package com.myrran.domain.entities.mob.enemy

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.Mob
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.entities.common.effectable.Effectable
import com.myrran.domain.entities.common.effectable.EffectableComponent
import com.myrran.domain.entities.common.proximityaware.ProximityAware
import com.myrran.domain.entities.common.proximityaware.ProximityAwareComponent
import com.myrran.domain.entities.common.steerable.Steerable
import com.myrran.domain.entities.common.steerable.SteerableComponent
import com.myrran.domain.entities.common.vulnerable.Vulnerable
import com.myrran.domain.entities.common.vulnerable.VulnerableComponent
import com.myrran.domain.misc.metrics.time.Second
import com.myrran.infraestructure.eventbus.EventDispatcher

data class Enemy(

    override val id: EntityId,
    override val steerable: SteerableComponent,
    val eventDispatcher: EventDispatcher,

    private val vulnerable: VulnerableComponent,
    private val effectable: EffectableComponent,
    private val proximity: ProximityAwareComponent

): Mob, Steerable by steerable, Corporeal, Disposable,
    Vulnerable by vulnerable, Effectable by effectable, ProximityAware by proximity
{
    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun act(deltaTime: Float)
    {
        steerable.update(deltaTime)
        effectable.updateEffects(this, Second(deltaTime))
    }

    override fun dispose() {

        steerable.dispose()
    }
}
