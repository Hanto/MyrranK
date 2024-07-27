package com.myrran.model.skills.skills.skill

import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotContent
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.skills.subskill.SubSkillSlot
import com.myrran.model.skills.skills.subskill.SubSkillSlotContent
import com.myrran.model.skills.skills.subskill.SubSkillSlotId
import com.myrran.model.skills.skills.subskill.SubSkillSlots
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.stat.StatsI
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.skills.BuffSkillTemplate
import com.myrran.model.skills.templates.skills.SkillTemplateId
import com.myrran.model.skills.templates.skills.SubSkillTemplate
import com.myrran.model.spells.spell.SkillType
import com.myrran.model.spells.spell.Spell

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

        type.builder.invoke(this)

    // SUBSKILLS BUFFSKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getSubSkillSlots(): Collection<SubSkillSlot> =

        slots.getSubSkillSlots()

    fun getSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent =

        slots.getSubSkill(subSkillSlotId)

    fun setSubSkill(subSkillSlotId: SubSkillSlotId, subSkillTemplate: SubSkillTemplate) =

        slots.setSubSkill(subSkillSlotId, subSkillTemplate)

    fun removeSubSkill(subSkillSlotId: SubSkillSlotId): SubSkillSlotContent =

        slots.removeSubSkill(subSkillSlotId)

    fun getBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slots.getBuffSkill(subSkillSlotId, buffSkillSlotId)

    fun setBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, template: BuffSkillTemplate) =

        slots.setBuffSkill(subSkillSlotId, buffSkillSlotId, template)

    fun removeBuffSkill(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId): BuffSkillSlotContent =

        slots.removeBuffSKill(subSkillSlotId, buffSkillSlotId)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(subSkillSlotId: SubSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots.upgrade(subSkillSlotId, statId, upgradeBy)

    fun upgrade(subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: Upgrades) =

        slots.upgrade(subSkillSlotId, buffSkillSlotId, statId, upgradeBy)

    fun baseCost(): UpgradeCost =

        stats.totalCost()
}
