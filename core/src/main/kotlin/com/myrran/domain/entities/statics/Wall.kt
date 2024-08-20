package com.myrran.domain.entities.statics

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.entities.common.steerable.Steerable
import com.myrran.domain.entities.common.steerable.SteerableComponent

class Wall(

    val steerable: SteerableComponent,

): Steerable by steerable, Corporeal, Disposable {

    override fun dispose() {

        steerable.dispose()
    }
}

