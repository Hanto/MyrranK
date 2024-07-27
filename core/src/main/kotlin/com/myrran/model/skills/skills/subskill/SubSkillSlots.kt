package com.myrran.model.skills.skills.subskill

import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotContent
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.skills.BuffSkillTemplate
import com.myrran.model.skills.templates.skills.SubSkillTemplate

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

    fun setSubSkill(subSkillSlotId: SubSkillSlotId, subSkillTemplate: SubSkillTemplate) =

        slotMap[subSkillSlotId]?.setSubSkill(subSkillTemplate)

    fun removeSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent =

        slotMap[subSkillSlotId]?.removeSubSkill()!!

    fun setBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplate: BuffSkillTemplate) =

        slotMap[subSkillSlotId]?.setBuffSkill(buffSkillSlotId, buffSkillTemplate)

    fun getBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slotMap[subSkillSlotId]?.getBuffSkill(buffSkillSlotId)!!

    fun removeBuffSKill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slotMap[subSkillSlotId]?.removeBuffSkill(buffSkillSlotId)!!

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

