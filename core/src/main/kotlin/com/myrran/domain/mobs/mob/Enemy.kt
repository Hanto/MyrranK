package com.myrran.domain.mobs.mob

import com.myrran.domain.World
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.steerable.Movable
import com.myrran.domain.mobs.common.steerable.Spatial
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableByBox2DComponent
import com.myrran.infraestructure.eventbus.EventDispatcher

data class Enemy(

    override val id: MobId,
    override val steerable: SteerableByBox2DComponent,
    val eventDispatcher: EventDispatcher


): Mob, Steerable by steerable, Spatial, Movable
{
    override fun act(deltaTime: Float, world: World)
    {
        steerable.update(deltaTime)
    }
}
