package com.myrran.domain.mobs.common.caster

import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.metrics.Second
import com.myrran.domain.skills.created.skill.SkillId

class CasterComponent(

    override var selectedSkillId: SkillId? = SkillId.from("5e2d588d-cc7e-4475-87da-622409e4eb31"),
    override var pointingAt: PositionMeters = PositionMeters(0f,0f)

): Caster
{
    private var isCasting: Boolean = false
    private var requiredTime: Second = Second(0)
    private var expendedTime: Second = Second(0)

    override fun isReadyToCast(): Boolean =

        !isCasting && selectedSkillId != null

    override fun setCastingTime(castingTime: Second) {

        isCasting = true
        requiredTime = castingTime
    }

    override fun updateCastingTime(deltaTime: Float) {

        if (isCasting) {

            expendedTime += Second.fromBox2DUnits(deltaTime)

            if (expendedTime >= requiredTime) {

                isCasting = false
                expendedTime = Second(0)
            }
        }
    }

    override fun updateTarget(target: PositionMeters) {

        pointingAt = target
    }
}
