package com.myrran.domain.mob

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2

class Player(

    private val steeringComponent: SteeringComponent

): Steerable<Vector2> by steeringComponent
