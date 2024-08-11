package com.myrran.domain.mob.steerable

import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.ai.steer.SteeringBehavior
import com.badlogic.gdx.ai.steer.behaviors.Arrive
import com.badlogic.gdx.ai.steer.behaviors.Flee
import com.badlogic.gdx.ai.steer.behaviors.Seek
import com.badlogic.gdx.ai.steer.behaviors.Wander
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2

class SteeringBehaviorsFactory
{
    fun wander(wanderer: Steerable<Vector2>): SteeringBehavior<Vector2> {

        return Wander(wanderer)
            .setFaceEnabled(false)
            .setWanderOffset(20f)
            .setWanderOrientation(0f)
            .setWanderRadius(10f)
            .setWanderRate(MathUtils.PI * 2)
    }

    fun seek(seeker: Steerable<Vector2>, target: Steerable<Vector2>): SteeringBehavior<Vector2> {

        return Seek(seeker, target)
    }

    fun flee(runner: Steerable<Vector2>, fleeingFrom: Steerable<Vector2>): SteeringBehavior<Vector2> {

        return Flee(runner, fleeingFrom)
    }

    fun arrive(runner: Steerable<Vector2>, target: Steerable<Vector2>): SteeringBehavior<Vector2> {

        return Arrive(runner, target)
            .setTimeToTarget(0.1f)
            .setArrivalTolerance(7f)
            .setDecelerationRadius(10f)
    }
}
