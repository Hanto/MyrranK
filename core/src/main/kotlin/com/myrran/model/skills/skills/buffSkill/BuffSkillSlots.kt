package com.myrran.model.skills.skills.buffSkill

import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.skills.BuffSkillTemplate

data class BuffSkillSlots(

    private val slotMap: Map<BuffSkillSlotId, BuffSkillSlot>
)
{
    constructor(slots: Collection<BuffSkillSlot>):
        this(slotMap = slots.associateBy { it.id } )

    // BDEBUFFSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkillSlots(): Collection<BuffSkillSlot> =

        slotMap.values

    fun getBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slotMap[buffSkillSlotId]?.content!!

    fun getBuffSkills(): Collection<BuffSkill> =

        slotMap.values.map { it.content }.filterIsInstance<BuffSkill>()

    fun setBuffSkill(buffSkillSlotId: BuffSkillSlotId, buffSkillTemplate: BuffSkillTemplate) =

        slotMap[buffSkillSlotId]?.setBuffSkill(buffSkillTemplate)

    fun removeBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slotMap[buffSkillSlotId]?.removeBuffSkill()!!

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slotMap[buffSkillSlotId]?.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        slotMap.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.sum(next) }
}
