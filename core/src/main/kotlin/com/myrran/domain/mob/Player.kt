package com.myrran.domain.mob

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2
import com.myrran.domain.mob.metrics.PositionMeters
import com.myrran.infraestructure.input.PlayerInputs
import com.myrran.infraestructure.input.State
import com.myrran.infraestructure.input.StateIddle

class Player(

    override val id: MobId,
    private val steeringComponent: SteeringComponent,
    var state: State = StateIddle(Vector2(0f, 0f))

): Steerable<Vector2> by steeringComponent, Mob
{
    var lastPosition: Vector2 = Vector2(0f, 0f)

    fun applyInputs(inputs: PlayerInputs) {

        state = state.nextState(inputs)
        steeringComponent.spatial.body.linearVelocity = state.direction.scl(4f, 4f)
    }

    override fun setPosition(position: PositionMeters) =

        steeringComponent.setPosition(position)

    override fun saveLastPosition() {

        lastPosition = position.cpy()
    }
}
