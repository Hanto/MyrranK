package com.myrran.domain.mobs.wall

import com.myrran.domain.mobs.common.corporeal.Corporeal
import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableComponent

class Wall(

    val steerable: SteerableComponent,

): Steerable by steerable, Corporeal

