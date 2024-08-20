package com.myrran.domain.entities.common.steerable

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2
import com.myrran.domain.entities.common.corporeal.Corporeal

interface Steerable: Steerable<Vector2>, Corporeal
