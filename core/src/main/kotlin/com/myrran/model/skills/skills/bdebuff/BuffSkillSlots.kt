package com.myrran.model.skills.skills.bdebuff

import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades

class BuffSkillSlots {

    private val slots = HashMap<BuffSkillSlotId, BuffSkillSlot>()

    fun getSlots(): Collection<BuffSkill> =

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
