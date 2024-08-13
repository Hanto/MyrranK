package com.myrran.domain.mobs.common

import com.myrran.domain.World
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.steerable.Movable
import com.myrran.domain.mobs.common.steerable.Spatial
import com.myrran.domain.mobs.common.steerable.Steerable

interface Mob: Steerable, Spatial, Movable, Identifiable<MobId> {

    val steerable: Steerable
    fun act(deltaTime: Float, world: World)
}
