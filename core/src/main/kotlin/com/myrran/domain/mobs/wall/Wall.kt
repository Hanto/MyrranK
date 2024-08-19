package com.myrran.domain.mobs.wall

import com.myrran.domain.mobs.common.steerable.Steerable
import com.myrran.domain.mobs.common.steerable.SteerableByBox2DComponent

class Wall(

    val steerable: SteerableByBox2DComponent,

): Steerable by steerable

