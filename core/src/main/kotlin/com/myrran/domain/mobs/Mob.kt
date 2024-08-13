package com.myrran.domain.mobs

import com.myrran.domain.World
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.steerable.Movable
import com.myrran.domain.mobs.common.steerable.Spatial
import com.myrran.domain.mobs.common.steerable.SteerableAI

interface Mob: SteerableAI, Spatial, Movable, Identifiable<MobId> {

    val steerable: SteerableAI
    fun act(deltaTime: Float, world: World)
}
