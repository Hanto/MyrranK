package com.myrran.domain.mobs.mob

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.corporeal.Corporeal
import com.myrran.domain.mobs.common.proximityaware.ProximityAware
import com.myrran.domain.mobs.common.proximityaware.ProximityAwareComponent
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableComponent
import com.myrran.infraestructure.eventbus.EventDispatcher

data class Enemy(

    override val id: MobId,
    override val steerable: SteerableComponent,
    val eventDispatcher: EventDispatcher,

    private val proximity: ProximityAwareComponent

): Mob, Identifiable<MobId>, Steerable by steerable, Corporeal, Disposable,
    ProximityAware by proximity
{
    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun act(deltaTime: Float)
    {
        steerable.update(deltaTime)
    }

    override fun dispose() {

        steerable.dispose()
    }
}
