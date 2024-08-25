package com.myrran.domain.entities.common.effectable

import com.myrran.domain.entities.mob.spells.effect.Effect

class EffectableComponent: Effectable {

    private val effects: MutableList<Effect> = mutableListOf()

    override fun addEffect(effect: Effect) {


    }
}
