package com.myrran.domain.mobs.wall

import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.corporeal.Corporeal
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableComponent

class Wall(

    override val id: MobId,
    override val steerable: SteerableComponent,

): Mob, Steerable by steerable, Corporeal {

    override fun act(deltaTime: Float) {
    }

    override fun dispose() {

        steerable.dispose()
    }
}

