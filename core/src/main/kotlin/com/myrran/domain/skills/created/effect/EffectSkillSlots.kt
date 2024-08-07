package com.myrran.domain.skills.created.effect

import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.templates.effect.EffectTemplate

data class EffectSkillSlots(

    private val bySlotId: Map<EffectSkillSlotId, EffectSkillSlot>
)
{
    // EFFECT SKILL:
    //--------------------------------------------------------------------------------------------------------

    fun getEffectSkillSlots(): Collection<EffectSkillSlot> =

        bySlotId.values

    fun getEffectSkill(effectSkillSlotId: EffectSkillSlotId): EffectSkill? =

        bySlotId[effectSkillSlotId]?.getEffectSkill()

    fun getEffectSkills(): Collection<EffectSkill> =

        bySlotId.values.map { it.content }.filterIsInstance<EffectSkill>()

    fun isEffectSkillSlotOpenedBy(effectSkillSlotId: EffectSkillSlotId, effectTemplate: EffectTemplate): Boolean =

        bySlotId[effectSkillSlotId]?.isOpenedBy(effectTemplate.keys) ?: false

    fun removeEffectSkillFrom(effectSkillSlotId: EffectSkillSlotId): EffectSkill? =

        bySlotId[effectSkillSlotId]?.removeEffectSkill()

    fun removeAllEffectSkills(): Collection<EffectSkill> =

        bySlotId.values.mapNotNull { removeEffectSkillFrom(it.id) }.filterIsInstance<EffectSkill>()

    fun setEffectSkill(effectSkillSlotId: EffectSkillSlotId, effectSkill: EffectSkill) =

        bySlotId[effectSkillSlotId]?.setEffectSkill(effectSkill)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(effectSkillSlotId: EffectSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        bySlotId[effectSkillSlotId]?.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        bySlotId.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.plus(next) }
}
