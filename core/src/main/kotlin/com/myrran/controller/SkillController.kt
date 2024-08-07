package com.myrran.controller

import com.myrran.application.SpellBook
import com.myrran.domain.skills.created.Skill
import com.myrran.domain.skills.created.buff.BuffSkillSlot
import com.myrran.domain.skills.created.buff.BuffSkillSlotId
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.subskill.SubSkillSlot
import com.myrran.domain.skills.created.subskill.SubSkillSlotId
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId

// BOOK
//------------------------------------------------------------------------------------------------------------

class BookSkillController(
    private val book: SpellBook,
)
{
    fun toSkillController(skill: Skill): SkillController =

        SkillController(
            skillId = skill.id,
            book = book,)
}

// SKILL:
//------------------------------------------------------------------------------------------------------------

class SkillController(

    private val skillId: SkillId,
    private val book: SpellBook,

): StatController
{
    override fun upgrade(stat: StatId, numUpgrades: NumUpgrades) =

        book.upgrade(skillId, stat, numUpgrades)

    fun removeSkill() =

        book.removeSkill(skillId)

    fun toSubSkillController(subSkillSlot: SubSkillSlot): SubSkillController =

        SubSkillController(
            skillId = skillId,
            subSlotId = subSkillSlot.id,
            book = book,)
}

// SUBSKILL:
//------------------------------------------------------------------------------------------------------------

class SubSkillController(

    private val skillId: SkillId,
    private val subSlotId: SubSkillSlotId,
    private val book: SpellBook,

): StatController
{
    override fun upgrade(stat: StatId, numUpgrades: NumUpgrades) =

        book.upgrade(skillId, subSlotId, stat, numUpgrades)

    fun isOpenedBy(subSkillTemplate: SubSkillTemplateId) =

        book.isSubSkillOpenedBy(skillId, subSlotId, subSkillTemplate)

    fun setSubSkill(subSkillTemplateId: SubSkillTemplateId) =

        book.setSubSkillTo(skillId, subSlotId, subSkillTemplateId)

    fun removeSubSkill() =

        book.removeSubSkill(skillId, subSlotId)

    fun toBuffSkillController(buffSkillSlot: BuffSkillSlot): BuffSKillController =

        BuffSKillController(
            skillId = skillId,
            subSlotId = subSlotId,
            buffSlotId = buffSkillSlot.id,
            book = book,)
}

// BUFFSKILL:
//------------------------------------------------------------------------------------------------------------

class BuffSKillController(

    private val skillId: SkillId,
    private val subSlotId: SubSkillSlotId,
    private val buffSlotId: BuffSkillSlotId,
    private val book: SpellBook,

): StatController
{
    override fun upgrade(stat: StatId, numUpgrades: NumUpgrades) =

        book.upgrade(skillId, subSlotId, buffSlotId, stat, numUpgrades)

    fun isOpenedBy(buffSkillTemplateId: BuffSkillTemplateId) =

        book.isBuffSkillOpenedBy(skillId, subSlotId, buffSlotId, buffSkillTemplateId)

    fun setBuffSkill(buffSkillTemplateId: BuffSkillTemplateId) =

        book.setBuffSKillTo(skillId, subSlotId, buffSlotId, buffSkillTemplateId)

    fun removeBuffSkill() =

        book.removeBuffSkill(skillId, subSlotId, buffSlotId)
}

interface StatController {

    fun upgrade(stat: StatId, numUpgrades: NumUpgrades): Unit?
}
