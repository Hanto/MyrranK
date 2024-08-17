package com.myrran.domain.mobs.mob

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.proximity.Proximity
import com.myrran.domain.mobs.common.proximity.ProximityComponent
import com.myrran.domain.mobs.common.steerable.Movable
import com.myrran.domain.mobs.common.steerable.Spatial
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableByBox2DComponent
import com.myrran.infraestructure.eventbus.EventDispatcher

data class Enemy(

    override val id: MobId,
    override val steerable: SteerableByBox2DComponent,
    val eventDispatcher: EventDispatcher,

    private val proximity: ProximityComponent

): Mob, Identifiable<MobId>, Steerable by steerable, Spatial, Movable, Disposable,
    Proximity by proximity
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
