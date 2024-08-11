package com.myrran.domain.mob

import com.myrran.domain.mob.metrics.SizePixels
import com.myrran.infraestructure.input.PlayerInputs

class PlayerFactory(

    private val bodyFactory: BodyFactory,
    private val playerInputs: PlayerInputs,
)
{
    fun createPlayer(): Player {

        val body = bodyFactory.createSquareBody(SizePixels(32, 32))
        val steeringComponent = SteeringComponent(Spatial(body), SpeedLimits())
        val playerInputs = playerInputs
        val player = Player(MobId(), steeringComponent, playerInputs)

        return player
    }
}
