package com.myrran.domain.skills.custom.subskill

import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotContent
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.UpgradeCost

data class SubSkillSlots(

    private val slotMap: Map<SubSkillSlotId, SubSkillSlot>
)
{
    // SUBSKILLS
    //--------------------------------------------------------------------------------------------------------

    fun getSubSkillSlots(): Collection<SubSkillSlot> =

        slotMap.values

    fun getSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent =

        slotMap[subSkillSlotId]?.content!!

    fun removeSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent? =

        slotMap[subSkillSlotId]?.removeSubSkill()

    fun setSubSkill(subSkillSlotId: SubSkillSlotId, subSkill: SubSkill) =

        slotMap[subSkillSlotId]?.setSubSkill(subSkill)

    // BUFFKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slotMap[subSkillSlotId]?.getBuffSkill(buffSkillSlotId)!!

    fun removeBuffSKill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent? =

        slotMap[subSkillSlotId]?.removeBuffSkill(buffSkillSlotId)

    fun setBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        slotMap[subSkillSlotId]?.setBuffSkill(buffSkillSlotId, buffSkill)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(slotId: SubSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        slotMap[slotId]?.upgrade(statId, upgradeBy)

    fun upgrade(slotId: SubSkillSlotId, buffSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        slotMap[slotId]?.upgrade(buffSlotId, statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        slotMap.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.plus(next) }
}

