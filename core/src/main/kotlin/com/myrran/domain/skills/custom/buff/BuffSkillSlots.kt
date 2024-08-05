package com.myrran.domain.skills.custom.buff

import com.myrran.domain.skills.custom.BuffSkill
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.templates.BuffSkillTemplate

data class BuffSkillSlots(

    private val bySlotId: Map<BuffSkillSlotId, BuffSkillSlot>
)
{
    // BDEBUFFSKILL:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkillSlots(): Collection<BuffSkillSlot> =

        bySlotId.values

    fun getBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkill? =

        bySlotId[buffSkillSlotId]?.getBuffSkill()

    fun getBuffSkills(): Collection<BuffSkill> =

        bySlotId.values.map { it.content }.filterIsInstance<BuffSkill>()

    fun removeBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkill? =

        bySlotId[buffSkillSlotId]?.removeBuffSkill()

    fun removeAllBuffSkills(): Collection<BuffSkill> =

        bySlotId.values.mapNotNull { removeBuffSkill(it.id) }.filterIsInstance<BuffSkill>()

    fun setBuffSkill(buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        bySlotId[buffSkillSlotId]?.setBuffSkill(buffSkill)

    fun isBuffSkillOpenedBy(buffSkillSlotId: BuffSkillSlotId, buffSkillTemplate: BuffSkillTemplate): Boolean =

        bySlotId[buffSkillSlotId]?.isOpenedBy(buffSkillTemplate.keys) ?: false

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        bySlotId[buffSkillSlotId]?.upgrade(statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        bySlotId.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.plus(next) }
}
