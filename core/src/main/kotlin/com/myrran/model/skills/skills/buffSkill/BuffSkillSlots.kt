package com.myrran.model.skills.skills.buffSkill

import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades

class BuffSkillSlots(

    slots: Collection<BuffSkillSlot>
)
{
    private val slots: Map<BuffSkillSlotId, BuffSkillSlot> = slots.associateBy { it.id }

    // BDEBUFFSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkillSlots(): Collection<BuffSkillSlot> =

        slots.values

    fun getBuffSkills(): Collection<BuffSkill> =

        slots.values.map { it.content }.filterIsInstance<BuffSkill>()

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots[buffSkillSlotId]?.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        slots.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.sum(next) }
}
