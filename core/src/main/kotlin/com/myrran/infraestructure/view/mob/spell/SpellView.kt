package com.myrran.infraestructure.view.mob.spell

import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mob.MobId

sealed interface SpellView: Identifiable<MobId>
{
    fun update(fractionOfTimestep: Float)
}
