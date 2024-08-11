package com.myrran.domain.mob

import com.myrran.domain.mob.metrics.SizePixels
import com.myrran.domain.mob.player.Player
import com.myrran.domain.mob.steerable.Spatial
import com.myrran.domain.mob.steerable.SpeedLimits
import com.myrran.domain.mob.steerable.SteeringComponent

class MobFactory(

    private val bodyFactory: BodyFactory,
)
{
    fun createPlayer(): Player {

        val body = bodyFactory.createSquareBody(SizePixels(32, 32))
        val steeringComponent = SteeringComponent(Spatial(body), SpeedLimits())
        val player = Player(MobId(), steeringComponent)

        return player
    }
}
