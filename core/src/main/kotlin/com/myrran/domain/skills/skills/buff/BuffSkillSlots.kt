package com.myrran.domain.skills.skills.buff

import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.UpgradeCost
import com.myrran.domain.skills.skills.stat.Upgrades

data class BuffSkillSlots(

    private val slotMap: Map<BuffSkillSlotId, BuffSkillSlot>
)
{
    // BDEBUFFSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkillSlots(): Collection<BuffSkillSlot> =

        slotMap.values

    fun getBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slotMap[buffSkillSlotId]?.content!!

    fun getBuffSkills(): Collection<BuffSkill> =

        slotMap.values.map { it.content }.filterIsInstance<BuffSkill>()

    fun removeBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slotMap[buffSkillSlotId]?.removeBuffSkill()!!

    fun setBuffSkill(buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        slotMap[buffSkillSlotId]?.setBuffSkill(buffSkill)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slotMap[buffSkillSlotId]?.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        slotMap.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.sum(next) }
}
