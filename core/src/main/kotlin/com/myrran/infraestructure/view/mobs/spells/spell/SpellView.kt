package com.myrran.infraestructure.view.mobs.spells.spell

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.MobId

sealed interface SpellView: Identifiable<MobId>, Disposable
{
    fun update(fractionOfTimestep: Float)
}
