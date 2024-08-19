package com.myrran.domain.mobs.spells.form

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.MobId
import com.myrran.domain.mobs.common.colisionable.Collisioner
import com.myrran.domain.mobs.common.consumable.Consumable
import com.myrran.domain.mobs.common.steerable.Movable
import com.myrran.domain.mobs.common.steerable.Spatial
import com.myrran.domain.mobs.common.steerable.Steerable

interface Form : Mob, Identifiable<MobId>, Steerable, Spatial, Movable, Disposable,
    Consumable, Collisioner
