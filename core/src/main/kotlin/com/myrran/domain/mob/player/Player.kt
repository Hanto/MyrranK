package com.myrran.domain.mob.player

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.mob.Mob
import com.myrran.domain.mob.MobId
import com.myrran.domain.mob.Movable
import com.myrran.domain.mob.steerable.SteeringComponent
import com.myrran.infraestructure.controller.PlayerInputs

class Player(

    override val id: MobId,
    private val movable: SteeringComponent,
    var state: State = StateIddle(Vector2(0f, 0f))

): Movable by movable, Mob
{
    fun applyInputs(inputs: PlayerInputs) {

        state = state.nextState(inputs)
        val force = state.direction.cpy().scl(1000f)
        movable.setLinearVelocity(state.direction, maxLinearSpeed)
        //movable.spatial.applyForceToCenter(force)
    }
}
