package com.myrran.domain.mobs.common.caster

import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.common.metrics.Second
import com.myrran.domain.skills.created.skill.Skill

class CasterComponent(

    private var selectedSkill: Skill? = null,
    override var pointingAt: PositionMeters = PositionMeters(0f,0f)

): Caster
{
    private var isCasting: Boolean = false
    private var requiredTime: Second = Second(0)
    private var expendedTime: Second = Second(0)

    override fun getSelectedSkill(): Skill? =

        selectedSkill

    override fun changeSelecctedSkillTo(newSkill: Skill) {

        if (!isCasting)
            selectedSkill = newSkill
    }

    override fun isReadyToCast(): Boolean =

        !isCasting && selectedSkill != null

    override fun startCasting() {

        if (isReadyToCast()) {

            isCasting = true
            expendedTime = Second(0)
            requiredTime = selectedSkill!!.getCastingTime()
        }
    }

    override fun stopCasting() {

        isCasting = false
        expendedTime = Second(0)
    }

    override fun updateCastingTime(deltaTime: Float) {

        if (isCasting) {

            expendedTime += Second.fromBox2DUnits(deltaTime)

            if (expendedTime >= requiredTime)
                stopCasting()
        }
    }

    override fun updateTarget(target: PositionMeters) {

        pointingAt = target
    }

    override fun getCastingInfo(): Caster.CastingInfo =

        when (requiredTime.isZero())
        {
            true -> Caster.CastingInfo(requiredTime, expendedTime, 0f)
            false -> Caster.CastingInfo(requiredTime, expendedTime, (expendedTime / requiredTime).coerceAtMost(1f))
        }

}
