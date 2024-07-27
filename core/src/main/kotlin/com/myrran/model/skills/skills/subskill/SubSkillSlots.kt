package com.myrran.model.skills.skills.subskill

import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades

class SubSkillSlots(

    slots: Collection<SubSkillSlot>
)
{
    private val slots: Map<SubSkillSlotId, SubSkillSlot> = slots.associateBy { it.id }

    // SUBSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun getSubSkillSlots(): Collection<SubSkillSlot> =

        slots.values

    fun getSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent =

        slots[subSkillSlotId]?.content!!

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

