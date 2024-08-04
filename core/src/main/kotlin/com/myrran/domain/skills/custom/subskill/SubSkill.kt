package com.myrran.domain.skills.custom.subskill

import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlot
import com.myrran.domain.skills.custom.buff.BuffSkillSlotContent
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.buff.BuffSkillSlots
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.stat.Stats
import com.myrran.domain.skills.custom.stat.StatsI
import com.myrran.domain.skills.custom.stat.UpgradeCost
import com.myrran.domain.skills.templates.LockType
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.spells.subspell.SubSkillType
import com.myrran.domain.spells.subspell.SubSpell

data class SubSkill(

    val id: SubSkillId,
    val templateId: SubSkillTemplateId,
    val type: SubSkillType,
    val name: SubSkillName,
    val stats: Stats,
    val slots: BuffSkillSlots,
    val keys: Collection<LockType>

): SubSkillSlotContent, StatsI by stats
{
    fun createSpell(): SubSpell =

        type.builder.invoke(this.copy())

    // BUFFSKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkillSlots(): Collection<BuffSkillSlot> =

        slots.getBuffSkillSlots()

    fun getBuffSkills(): Collection<BuffSkill> =

        slots.getBuffSkills()

    fun getBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slots.getBuffSkill(buffSkillSlotId)

    fun removeBuffSkill(buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slots.removeBuffSkill(buffSkillSlotId)

    fun setBuffSkill(buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        slots.setBuffSkill(buffSkillSlotId, buffSkill)

    fun isOpenedBy(buffSkillSlotId: BuffSkillSlotId, buffSkillTemplate: BuffSkillTemplate): Boolean =

        slots.getBuffSkills().none { it.templateId == buffSkillTemplate.id } &&
        slots.isBuffSkillOpenedBy(buffSkillSlotId, buffSkillTemplate)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        slots.upgrade(buffSkillSlotId, statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        stats.statCost() + slots.totalCost()

}
