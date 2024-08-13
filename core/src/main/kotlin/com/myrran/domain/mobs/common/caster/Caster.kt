package com.myrran.domain.mobs.common.caster

import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.metrics.Second
import com.myrran.domain.skills.created.skill.SkillId

interface Caster {

    var selectedSkillId: SkillId?
    var pointingAt: PositionMeters

    fun isReadyToCast(): Boolean
    fun setCastingTime(castingTime: Second)
    fun updateCastingTime(deltaTime: Float)
    fun updateTarget(target: PositionMeters)
}
