package com.myrran.domain.mobs.common

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.corporeal.Movable
import com.myrran.domain.mobs.common.corporeal.Spatial
import com.myrran.domain.mobs.common.steerable.Steerable

interface Mob: Identifiable<MobId>, Steerable, Spatial, Movable, Disposable
{
    val steerable: Steerable
    fun act(deltaTime: Float)
}
