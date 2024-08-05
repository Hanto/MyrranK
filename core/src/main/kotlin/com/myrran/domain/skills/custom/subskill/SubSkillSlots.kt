package com.myrran.domain.skills.custom.subskill

import com.myrran.domain.skills.custom.BuffSkill
import com.myrran.domain.skills.custom.SubSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplate

data class SubSkillSlots(

    private val bySlotId: Map<SubSkillSlotId, SubSkillSlot>,
)
{
    // SUBSKILLS
    //--------------------------------------------------------------------------------------------------------

    fun getSubSkillSlots(): Collection<SubSkillSlot> =

        bySlotId.values

    fun getSubSkill(subSkillSlotId: SubSkillSlotId): SubSkill? =

        bySlotId[subSkillSlotId]?.getSubSkill()

    fun removeSubSkill(subSkillSlotId: SubSkillSlotId): SubSkill? =

        bySlotId[subSkillSlotId]?.removeSubSkill()

    fun removeAllSubSkills(): Collection<SubSkill> =

        bySlotId.values.mapNotNull { removeSubSkill(it.id) }.filterIsInstance<SubSkill>()

    fun setSubSkill(subSkillSlotId: SubSkillSlotId, subSkill: SubSkill) =

        bySlotId[subSkillSlotId]?.setSubSkill(subSkill)

    fun isSubSkillOpenedBy(subSkillSlotId: SubSkillSlotId, subSkillTemplate: SubSkillTemplate): Boolean =

        bySlotId[subSkillSlotId]?.isSubSkillOpenedBy(subSkillTemplate) ?: false

    // BUFFKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkill? =

        bySlotId[subSkillSlotId]?.getBuffSkill(buffSkillSlotId)

    fun removeBuffSKill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkill? =

        bySlotId[subSkillSlotId]?.removeBuffSkill(buffSkillSlotId)

    fun removeAllBuffSkills(): Collection<BuffSkill> =

        bySlotId.values.flatMap { it.removeAllBuffSkills() }

    fun setBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        bySlotId[subSkillSlotId]?.setBuffSkill(buffSkillSlotId, buffSkill)

    fun isBuffSkillOpenedBy(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplate: BuffSkillTemplate): Boolean =

        bySlotId[subSkillSlotId]?.isBuffSkillOpenedBy(buffSkillSlotId, buffSkillTemplate) ?: false

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(slotId: SubSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        bySlotId[slotId]?.upgrade(statId, upgradeBy)

    fun upgrade(slotId: SubSkillSlotId, buffSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        bySlotId[slotId]?.upgrade(buffSlotId, statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        bySlotId.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.plus(next) }
}

