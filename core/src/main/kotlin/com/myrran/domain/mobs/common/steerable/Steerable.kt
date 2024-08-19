package com.myrran.domain.mobs.common.steerable

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2
import com.myrran.domain.mobs.common.corporeal.Corporeal

interface Steerable: Steerable<Vector2>, Corporeal
