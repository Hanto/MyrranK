package com.myrran.domain.mobs.common.steerable

import com.badlogic.gdx.ai.steer.Proximity
import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.ai.steer.SteeringBehavior
import com.badlogic.gdx.ai.steer.behaviors.Arrive
import com.badlogic.gdx.ai.steer.behaviors.CollisionAvoidance
import com.badlogic.gdx.ai.steer.behaviors.Flee
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering
import com.badlogic.gdx.ai.steer.behaviors.Pursue
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

    fun pursue(seeker: Steerable<Vector2>, target: Steerable<Vector2>): SteeringBehavior<Vector2> =

        Pursue(seeker, target)

    fun pursueAndEvadeEnemies(seeker: Steerable<Vector2>, target: Steerable<Vector2>): SteeringBehavior<Vector2> {

        val formation = CollisionAvoidance(seeker, seeker as Proximity<Vector2>)
        val seek = pursue(seeker, target)

        val prioritySteering = PrioritySteering(seeker, 0.0001f)
        prioritySteering.add(formation)
        prioritySteering.add(seek)

        return prioritySteering

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
