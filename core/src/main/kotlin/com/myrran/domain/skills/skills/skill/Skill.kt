package com.myrran.domain.skills.skills.skill

import com.myrran.domain.skills.skills.buff.BuffSkill
import com.myrran.domain.skills.skills.buff.BuffSkillSlotContent
import com.myrran.domain.skills.skills.buff.BuffSkillSlotId
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.Stats
import com.myrran.domain.skills.skills.stat.StatsI
import com.myrran.domain.skills.skills.stat.UpgradeCost
import com.myrran.domain.skills.skills.stat.Upgrades
import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlot
import com.myrran.domain.skills.skills.subskill.SubSkillSlotContent
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId
import com.myrran.domain.skills.skills.subskill.SubSkillSlots
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.spells.spell.SkillType
import com.myrran.domain.spells.spell.Spell

data class Skill(

    val id: SkillId,
    val templateId: SkillTemplateId,
    val type: SkillType,
    val name: SkillName,
    private val stats: Stats,
    private val slots: SubSkillSlots

): StatsI by stats
{
    fun createSpell(): Spell =

        type.builder.invoke(this.copy())

    // SUBSKILLS
    //--------------------------------------------------------------------------------------------------------

    fun getSubSkillSlots(): Collection<SubSkillSlot> =

        slots.getSubSkillSlots()

    fun getSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent =

        slots.getSubSkill(subSkillSlotId)

    fun removeSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent =

        slots.removeSubSkill(subSkillSlotId)

    fun setSubSkill(subSkillSlotId: SubSkillSlotId, subSkill: SubSkill) =

        slots.setSubSkill(subSkillSlotId, subSkill)

    // BUFFSKILL
    //--------------------------------------------------------------------------------------------------------

    fun getBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slots.getBuffSkill(subSkillSlotId, buffSkillSlotId)

    fun removeBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slots.removeBuffSKill(subSkillSlotId, buffSkillSlotId)

    fun setBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) =

        slots.setBuffSkill(subSkillSlotId, buffSkillSlotId, buffSkill)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(subSkillSlotId: SubSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots.upgrade(subSkillSlotId, statId, upgradeBy)

    fun upgrade(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots.upgrade(subSkillSlotId, buffSkillSlotId, statId, upgradeBy)

    fun baseCost(): UpgradeCost =

        stats.totalCost()
}
