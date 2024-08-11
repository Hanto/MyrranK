package com.myrran.domain.mob.steerable

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.ai.GdxAI

class SteeringSystem(

    private val componentMapper: ComponentMapper<SteeringComponent>

): IteratingSystem(Family.all(SteeringComponent::class.java).get())
{
    override fun update(deltaTime: Float) {

        super.update(deltaTime)
        GdxAI.getTimepiece().update(deltaTime)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {

        val steer = componentMapper.get(entity)
        steer.update(deltaTime)
    }
}
