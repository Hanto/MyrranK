package com.myrran.domain.mob

import com.myrran.domain.mob.metrics.Meter
import com.myrran.domain.mob.metrics.SizePixels
import com.myrran.domain.mob.metrics.Speed
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
        val limiter = SpeedLimits(
            maxLinearSpeed = Speed(Meter(4f))
        )
        val location = Spatial(body, limiter)
        val steeringComponent = SteeringComponent(location, limiter)
        val player = Player(MobId(), steeringComponent)

        return player
    }
}
