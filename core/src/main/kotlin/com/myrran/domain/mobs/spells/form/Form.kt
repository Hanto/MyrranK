package com.myrran.domain.mobs.spells.form

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.collisionable.Collisioner
import com.myrran.domain.mobs.common.consumable.Consumable
import com.myrran.domain.mobs.common.corporeal.Movable
import com.myrran.domain.mobs.common.corporeal.Spatial
import com.myrran.domain.mobs.common.steerable.Steerable

interface Form : Mob, Identifiable<MobId>, Steerable, Spatial, Movable, Disposable,
    Consumable, Collisioner
