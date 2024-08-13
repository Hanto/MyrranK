package com.myrran.domain.mobs.common.steerable

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.ai.GdxAI

class SteeringSystem(

    private val componentMapper: ComponentMapper<SteerableByBox2D>

): IteratingSystem(Family.all(SteerableByBox2D::class.java).get())
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
