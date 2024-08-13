package com.myrran.domain.mob

import com.myrran.domain.World
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mob.steerable.Movable
import com.myrran.domain.mob.steerable.Spatial
import com.myrran.domain.mob.steerable.SteerableAI

interface Mob: SteerableAI, Spatial, Movable, Identifiable<MobId> {

    val steerable: SteerableAI
    fun act(deltaTime: Float, world: World)
}
