package com.myrran.model.skills.custom.skill.subskill.buff

import com.myrran.model.skills.custom.stat.StatId
import com.myrran.model.skills.custom.stat.UpgradeCost
import com.myrran.model.skills.custom.stat.Upgrades

class BuffSkillSlots {

    private val slots = HashMap<BuffSkillSlotId, BuffSkillSlot>()

    fun getSlots(): Collection<BuffSkill> =

        slots.values.mapNotNull { it.content }

    fun totalCost(): UpgradeCost =

        slots.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.sum(next) }

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots[buffSkillSlotId]?.upgrade(statId, upgradeBy)
}
