package com.myrran.model.skills.skills.subskill

import com.myrran.model.skills.skills.bdebuff.BuffSkillSlotId
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades

class SubSkillSlots {

    private val slots = HashMap<SubSkillSlotId, SubSkillSlot>()

    fun getSlot(subSkillSlotId: SubSkillSlotId): SubSkill? =

        slots[subSkillSlotId]?.content

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(slotId: SubSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots[slotId]?.upgrade(statId, upgradeBy)

    fun upgrade(slotId: SubSkillSlotId, buffSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots[slotId]?.upgrade(buffSlotId, statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        slots.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.sum(next) }
}

