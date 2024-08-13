package com.myrran.domain.mob.steerable

import com.badlogic.gdx.ai.steer.Limiter
import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2

interface SteerableAI: Steerable<Vector2>, Movable, Spatial, Limiter
