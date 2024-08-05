package com.myrran.domain.skills.custom.subskill

import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotContent
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

    fun getSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent =

        bySlotId[subSkillSlotId]?.content!!

    fun removeSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent? =

        bySlotId[subSkillSlotId]?.removeSubSkill()

    fun removeAllSubSkills(): Collection<SubSkillSlotContent> =

        bySlotId.values.mapNotNull { removeSubSkill(it.id) }

    fun setSubSkill(subSkillSlotId: SubSkillSlotId, subSkill: SubSkill) =

        bySlotId[subSkillSlotId]?.setSubSkill(subSkill)

    fun isSubSkillOpenedBy(subSkillSlotId: SubSkillSlotId, subSkillTemplate: SubSkillTemplate): Boolean =

        bySlotId[subSkillSlotId]?.isSubSkillOpenedBy(subSkillTemplate) ?: false

    // BUFFKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        bySlotId[subSkillSlotId]?.getBuffSkill(buffSkillSlotId)!!

    fun removeBuffSKill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent? =

        bySlotId[subSkillSlotId]?.removeBuffSkill(buffSkillSlotId)

    fun removeAllBuffSkills(): Collection<BuffSkillSlotContent> =

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

