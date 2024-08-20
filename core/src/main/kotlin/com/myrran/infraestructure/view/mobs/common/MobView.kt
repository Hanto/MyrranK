package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.misc.Identifiable

interface MobView: Identifiable<EntityId>, Disposable {

    fun updatePosition(fractionOfTimestep: Float)
}
