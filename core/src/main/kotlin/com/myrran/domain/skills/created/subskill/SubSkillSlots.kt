package com.myrran.domain.skills.created.subskill

import com.myrran.domain.skills.created.BuffSkill
import com.myrran.domain.skills.created.SubSkill
import com.myrran.domain.skills.created.buff.BuffSkillSlotId
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.domain.skills.templates.SubSkillTemplate

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

    fun isSubSkillOpenedBy(subSkillSlotId: SubSkillSlotId, subSkillTemplate: SubSkillTemplate): Boolean =

        bySlotId[subSkillSlotId]?.isSubSkillOpenedBy(subSkillTemplate) ?: false

    fun removeSubSkill(subSkillSlotId: SubSkillSlotId): SubSkill? =

        bySlotId[subSkillSlotId]?.removeSubSkill()

    fun removeAllSubSkills(): Collection<SubSkill> =

        bySlotId.values.mapNotNull { removeSubSkill(it.id) }.filterIsInstance<SubSkill>()

    fun setSubSkill(subSkillSlotId: SubSkillSlotId, subSkill: SubSkill) =

        bySlotId[subSkillSlotId]?.setSubSkill(subSkill)

    // BUFFKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkill? =

        bySlotId[subSkillSlotId]?.getBuffSkill(buffSkillSlotId)

    fun isBuffSkillOpenedBy(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplate: BuffSkillTemplate): Boolean =

        bySlotId[subSkillSlotId]?.isBuffSkillOpenedBy(buffSkillSlotId, buffSkillTemplate) ?: false

    fun removeBuffSKill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkill? =

        bySlotId[subSkillSlotId]?.removeBuffSkill(buffSkillSlotId)

    fun removeAllBuffSkills(): Collection<BuffSkill> =

        bySlotId.values.flatMap { it.removeAllBuffSkills() }

    fun setBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        bySlotId[subSkillSlotId]?.setBuffSkill(buffSkillSlotId, buffSkill)

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

