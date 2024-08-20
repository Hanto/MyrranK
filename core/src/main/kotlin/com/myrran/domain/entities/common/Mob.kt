package com.myrran.domain.entities.common

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.corporeal.Movable
import com.myrran.domain.entities.common.corporeal.Spatial
import com.myrran.domain.entities.common.steerable.Steerable

interface Mob: Entity, Steerable, Spatial, Movable, Disposable
{
    val steerable: Steerable
    fun act(deltaTime: Float)
}
