package com.myrran.domain.entities.mob.spells.spell

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.entities.common.Mob
import com.myrran.domain.entities.common.collisioner.Collisioner
import com.myrran.domain.entities.common.consumable.Consumable
import com.myrran.domain.entities.common.corporeal.Movable
import com.myrran.domain.entities.common.corporeal.Spatial
import com.myrran.domain.entities.common.steerable.Steerable

sealed interface Spell: Mob, Steerable, Spatial, Movable, Disposable,
    Consumable, Collisioner
