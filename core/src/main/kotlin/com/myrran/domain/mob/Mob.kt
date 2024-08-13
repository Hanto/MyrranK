package com.myrran.domain.mob

import com.myrran.application.World
import com.myrran.domain.misc.Identifiable

interface Mob: Movable, Identifiable<MobId> {

    fun act(deltaTime: Float, world: World)
}
