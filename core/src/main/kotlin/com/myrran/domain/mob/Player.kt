package com.myrran.domain.mob

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2
import com.myrran.infraestructure.input.PlayerInputs
import com.myrran.infraestructure.input.State
import com.myrran.infraestructure.input.StateIddle

class Player(

    private val steeringComponent: SteeringComponent,
    private val input: PlayerInputs,
    var state: State = StateIddle(Vector2(0f, 0f))

): Steerable<Vector2> by steeringComponent
{
    var lastPosition: Vector2 = Vector2(0f, 0f)

    fun update(deltaTime: Float) {

        state = state.nextState(input)

        steeringComponent.spatial.body.linearVelocity = state.direction.scl(1.3f, 1.3f)
    }

    fun saveLastPosition() {

        lastPosition = position.cpy()
    }
}
