package com.myrran.domain.entities.common.corporeal

import com.myrran.domain.entities.common.movementlimiter.MovementLimiter

interface Corporeal: Spatial, Movable, MovementLimiter
