package com.myrran.domain.mob

import com.myrran.domain.misc.Identifiable

interface Mob: Movable, Identifiable<MobId> {
}
