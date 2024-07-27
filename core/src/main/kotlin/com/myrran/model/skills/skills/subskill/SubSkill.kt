package com.myrran.model.skills.skills.subskill

import com.myrran.model.skills.skills.buffSkill.BuffSkill
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlot
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlots
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.stat.StatsI
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.skills.BuffSkillTemplate
import com.myrran.model.spells.subspells.SubSkillType
import com.myrran.model.spells.subspells.SubSpell

data class SubSkill(

    val id: SubSkillId,
    val type: SubSkillType,
    val name: SubSkillName,
    val stats: Stats,
    val slots: BuffSkillSlots

): SubSkillSlotContent, StatsI by stats
{
    fun createSpell(): SubSpell =

        type.builder.invoke(this)

    // BDEBUFFSKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkillSlots(): Collection<BuffSkillSlot> =

        slots.getBuffSkillSlots()

    fun getBuffSkills(): Collection<BuffSkill> =

        slots.getBuffSkills()

    fun setBuffSkill(buffSkillSlotId: BuffSkillSlotId, buffSkillTemplate: BuffSkillTemplate) =

        slots.setBuffSkill(buffSkillSlotId, buffSkillTemplate)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots.upgrade(buffSkillSlotId, statId, upgradeBy)

    fun baseCost(): UpgradeCost =

        stats.totalCost()
}
