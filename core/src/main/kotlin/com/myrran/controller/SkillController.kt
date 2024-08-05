package com.myrran.controller

import com.myrran.domain.skills.book.PlayerSkillBook
import com.myrran.domain.skills.custom.buff.BuffSkillSlot
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.subskill.SubSkillSlot
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId

// BOOK
//------------------------------------------------------------------------------------------------------------

class BookSkillController(
    private val book: PlayerSkillBook,
)
{
    fun toSkillController(skillId: SkillId): SkillController =

        SkillController(
            skillId = skillId,
            book = book,)
}

// SKILL:
//------------------------------------------------------------------------------------------------------------

class SkillController(

    private val skillId: SkillId,
    private val book: PlayerSkillBook,

): StatController
{
    override fun upgrade(stat: StatId, numUpgrades: NumUpgrades) =

        book.upgrade(skillId, stat, numUpgrades)

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
    private val book: PlayerSkillBook,

): StatController
{
    override fun upgrade(stat: StatId, numUpgrades: NumUpgrades) =

        book.upgrade(skillId, subSlotId, stat, numUpgrades)

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
    private val book: PlayerSkillBook,

): StatController
{
    override fun upgrade(stat: StatId, numUpgrades: NumUpgrades) =

        book.upgrade(skillId, subSlotId, buffSlotId, stat, numUpgrades)

    fun isOpenedBy(buffSkillTemplateId: BuffSkillTemplateId) =

        book.isBuffSkillOpenedBy(skillId, subSlotId, buffSlotId, buffSkillTemplateId)

    fun addBuffSkill(buffSkillTemplateId: BuffSkillTemplateId) =

        book.addBuffSKillTo(skillId, subSlotId, buffSlotId, buffSkillTemplateId)

    fun removeBuffSkill() =

        book.removeBuffSkill(skillId, subSlotId, buffSlotId)
}

interface StatController {

    fun upgrade(stat: StatId, numUpgrades: NumUpgrades): Unit?
}
