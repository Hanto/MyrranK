package com.myrran.infraestructure.view.mobs.spells.spell

import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.MobId

sealed interface SpellView: Identifiable<MobId>
{
    fun update(fractionOfTimestep: Float)
}
