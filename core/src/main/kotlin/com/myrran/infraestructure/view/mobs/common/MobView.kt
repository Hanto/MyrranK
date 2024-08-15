package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.MobId

interface MobView: Identifiable<MobId>, Disposable {

    fun update(fractionOfTimestep: Float)
}
