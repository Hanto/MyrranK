package com.myrran.domain.skills.custom.buff

import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate

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

    fun isBuffSkillOpenedBy(buffSkillSlotId: BuffSkillSlotId, buffSkillTemplate: BuffSkillTemplate): Boolean =

        slotMap[buffSkillSlotId]?.isOpenedBy(buffSkillTemplate.keys) ?: false

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        slotMap[buffSkillSlotId]?.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        slotMap.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.plus(next) }
}
