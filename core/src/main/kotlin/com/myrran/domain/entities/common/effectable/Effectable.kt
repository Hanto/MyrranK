package com.myrran.domain.entities.common.effectable

import com.myrran.domain.entities.mob.spells.effect.Effect

interface Effectable {

    fun addEffect(effect: Effect)
}
