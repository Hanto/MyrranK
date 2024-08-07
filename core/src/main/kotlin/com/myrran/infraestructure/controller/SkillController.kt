package com.myrran.infraestructure.controller

import com.myrran.application.SpellBook
import com.myrran.domain.skills.created.effect.EffectSkillSlot
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.form.FormSkillSlot
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.skills.created.skill.SkillName
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.templates.effect.EffectTemplateId
import com.myrran.domain.skills.templates.form.FormTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId

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

    fun toSkillTemplateController(): SkillTemplateController =

        SkillTemplateController(
            book = book)
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

    fun changeName(newName: String) =

        book.changeName(skillId, SkillName(newName))

    fun removeSkill() =

        book.removeSkill(skillId)

    fun toFormSkillController(formSkillSlot: FormSkillSlot): FormSkillController =

        FormSkillController(
            skillId = skillId,
            effectSlotId = formSkillSlot.id,
            book = book,)
}

// FORMSKILL:
//------------------------------------------------------------------------------------------------------------

class FormSkillController(

    private val skillId: SkillId,
    private val effectSlotId: FormSkillSlotId,
    private val book: SpellBook,

): StatController
{
    override fun upgrade(stat: StatId, numUpgrades: NumUpgrades) =

        book.upgrade(skillId, effectSlotId, stat, numUpgrades)

    fun isOpenedBy(formTemplateId: FormTemplateId) =

        book.isFormSkillSlotOpenedBy(skillId, effectSlotId, formTemplateId)

    fun setFormSkill(formTemplateId: FormTemplateId) =

        book.setFormSkillTo(skillId, effectSlotId, formTemplateId)

    fun removeFormSkill() =

        book.removeFormSkillFrom(skillId, effectSlotId)

    fun toEffectSkillController(effectSkillSlot: EffectSkillSlot): EffectSKillController =

        EffectSKillController(
            skillId = skillId,
            formSlotId = effectSlotId,
            effectSlotId = effectSkillSlot.id,
            book = book,)
}

// EFFECT SKILL:
//------------------------------------------------------------------------------------------------------------

class EffectSKillController(

    private val skillId: SkillId,
    private val formSlotId: FormSkillSlotId,
    private val effectSlotId: EffectSkillSlotId,
    private val book: SpellBook,

): StatController
{
    override fun upgrade(stat: StatId, numUpgrades: NumUpgrades) =

        book.upgrade(skillId, formSlotId, effectSlotId, stat, numUpgrades)

    fun isOpenedBy(effectTemplateId: EffectTemplateId) =

        book.isEffectSkillSlotOpenedBy(skillId, formSlotId, effectSlotId, effectTemplateId)

    fun setEffectSkill(effectTemplateId: EffectTemplateId) =

        book.setEffectSkillTo(skillId, formSlotId, effectSlotId, effectTemplateId)

    fun removeEffectSkill() =

        book.removeEffectSkillFrom(skillId, formSlotId, effectSlotId)
}

interface StatController {

    fun upgrade(stat: StatId, numUpgrades: NumUpgrades): Unit?
}

// SKILLTEMPLATE:
//------------------------------------------------------------------------------------------------------------

class SkillTemplateController(

    private val book: SpellBook
)
{
    fun addSkill(id: SkillTemplateId) =

        book.addSkill(id)
}
