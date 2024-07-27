package com.myrran.model.skills.skills.buffSkill

import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades

class BuffSkillSlots {

    private val slots = HashMap<BuffSkillSlotId, BuffSkillSlot>()

    // BDEBUFFSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkillSlots(): Collection<BuffSkillSlot> =

        slots.values

    fun getBuffSkills(): Collection<BuffSkill> =

        slots.values.mapNotNull { it.content }

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots[buffSkillSlotId]?.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        slots.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.sum(next) }

}
