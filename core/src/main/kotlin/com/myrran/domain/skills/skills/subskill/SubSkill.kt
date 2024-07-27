package com.myrran.domain.skills.skills.subskill

import com.myrran.domain.skills.skills.buffskill.BuffSkill
import com.myrran.domain.skills.skills.buffskill.BuffSkillSlot
import com.myrran.domain.skills.skills.buffskill.BuffSkillSlotContent
import com.myrran.domain.skills.skills.buffskill.BuffSkillSlotId
import com.myrran.domain.skills.skills.buffskill.BuffSkillSlots
import com.myrran.domain.skills.stat.StatId
import com.myrran.domain.skills.stat.Stats
import com.myrran.domain.skills.stat.StatsI
import com.myrran.domain.skills.stat.UpgradeCost
import com.myrran.domain.skills.stat.Upgrades
import com.myrran.domain.skills.templates.LockTypes
import com.myrran.domain.skills.templates.skills.SubSkillTemplateId
import com.myrran.domain.spells.subspell.SubSkillType
import com.myrran.domain.spells.subspell.SubSpell

data class SubSkill(

    val id: SubSkillId,
    val templateId: SubSkillTemplateId,
    val type: SubSkillType,
    val name: SubSkillName,
    val stats: Stats,
    val slots: BuffSkillSlots,
    val keys: Collection<LockTypes>

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

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots.upgrade(buffSkillSlotId, statId, upgradeBy)

    fun baseCost(): UpgradeCost =

        stats.totalCost()
}
