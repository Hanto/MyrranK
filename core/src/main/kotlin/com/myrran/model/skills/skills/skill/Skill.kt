package com.myrran.model.skills.skills.skill

import com.myrran.model.skills.skills.buffSkill.BuffSkill
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotContent
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.skills.subskill.SubSkill
import com.myrran.model.skills.skills.subskill.SubSkillSlot
import com.myrran.model.skills.skills.subskill.SubSkillSlotContent
import com.myrran.model.skills.skills.subskill.SubSkillSlotId
import com.myrran.model.skills.skills.subskill.SubSkillSlots
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.stat.StatsI
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.skills.SkillTemplateId
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
