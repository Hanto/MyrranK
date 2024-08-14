package com.myrran.domain.mobs.common.caster

import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.skills.created.skill.Skill

interface Caster {

    var pointingAt: PositionMeters

    fun isReadyToCast(): Boolean
    fun getSelectedSkill(): Skill?
    fun changeSelecctedSkillTo(newSkill: Skill)
    fun startCasting()
    fun stopCasting()
    fun updateCastingTime(deltaTime: Float)
    fun updateTarget(target: PositionMeters)
}
