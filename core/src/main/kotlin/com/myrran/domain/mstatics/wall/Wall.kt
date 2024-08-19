package com.myrran.domain.mstatics.wall

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.mobs.common.corporeal.Corporeal
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableComponent

class Wall(

    val steerable: SteerableComponent,

): Steerable by steerable, Corporeal, Disposable {

    override fun dispose() {

        steerable.dispose()
    }
}

