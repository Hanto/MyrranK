package com.myrran.model.skills.skills.buffSkill

import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.skills.BuffSkillTemplate

data class BuffSkillSlots(

    val slots: Collection<BuffSkillSlot>
)
{
    private val slotMap: Map<BuffSkillSlotId, BuffSkillSlot> = slots.associateBy { it.id }

    // BDEBUFFSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkillSlots(): Collection<BuffSkillSlot> =

        slotMap.values

    fun getBuffSkills(): Collection<BuffSkill> =

        slotMap.values.map { it.content }.filterIsInstance<BuffSkill>()

    fun setBuffSkill(buffSkillSlotId: BuffSkillSlotId, buffSkillTemplate: BuffSkillTemplate) =

        slotMap[buffSkillSlotId]?.setBuffSkill(buffSkillTemplate)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slotMap[buffSkillSlotId]?.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        slotMap.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.sum(next) }
}
