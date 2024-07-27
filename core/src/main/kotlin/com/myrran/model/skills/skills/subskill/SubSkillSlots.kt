package com.myrran.model.skills.skills.subskill

import com.myrran.model.skills.skills.buffSkill.BuffSkill
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotContent
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades

data class SubSkillSlots(

    private val slotMap: Map<SubSkillSlotId, SubSkillSlot>
)
{
    constructor(slots: Collection<SubSkillSlot>):
        this(slotMap = slots.associateBy { it.id } )

    // SUBSKILL BUFFSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun getSubSkillSlots(): Collection<SubSkillSlot> =

        slotMap.values

    fun getSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent =

        slotMap[subSkillSlotId]?.content!!

    fun removeSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent =

        slotMap[subSkillSlotId]?.removeSubSkill()!!

    fun setSubSkill(subSkillSlotId: SubSkillSlotId, subSkill: SubSkill) =

        slotMap[subSkillSlotId]?.setSubSkill(subSkill)

    fun getBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slotMap[subSkillSlotId]?.getBuffSkill(buffSkillSlotId)!!

    fun removeBuffSKill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slotMap[subSkillSlotId]?.removeBuffSkill(buffSkillSlotId)!!

    fun setBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        slotMap[subSkillSlotId]?.setBuffSkill(buffSkillSlotId, buffSkill)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(slotId: SubSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slotMap[slotId]?.upgrade(statId, upgradeBy)

    fun upgrade(slotId: SubSkillSlotId, buffSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slotMap[slotId]?.upgrade(buffSlotId, statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        slotMap.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.sum(next) }
}

