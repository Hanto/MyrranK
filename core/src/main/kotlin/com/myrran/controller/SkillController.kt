package com.myrran.controller

import com.myrran.domain.skills.book.PlayerSkillBook
import com.myrran.domain.skills.custom.buff.BuffSkillSlot
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.subskill.SubSkillSlot
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId

// SKILL:
//------------------------------------------------------------------------------------------------------------

class SkillController(

    private val skillId: SkillId,
    private val book: PlayerSkillBook,
    val dadManager: DragAndDropManager

): StatController
{
    override fun upgrade(stat: StatId, numUpgrades: NumUpgrades) =

        book.upgrade(skillId, stat, numUpgrades)

    fun toSubSkillController(subSkillSlot: SubSkillSlot): SubSkillController =

        SubSkillController(
            skillId = skillId,
            subSlotId = subSkillSlot.id,
            book = book,
            dadManager = dadManager)
}

// SUBSKILL:
//------------------------------------------------------------------------------------------------------------

class SubSkillController(

    private val skillId: SkillId,
    private val subSlotId: SubSkillSlotId,
    private val book: PlayerSkillBook,
    val dadManager: DragAndDropManager,

): StatController
{
    override fun upgrade(stat: StatId, numUpgrades: NumUpgrades) =

        book.upgrade(skillId, subSlotId, stat, numUpgrades)

    fun toBuffSkillController(buffSkillSlot: BuffSkillSlot): BuffSKillController =

        BuffSKillController(
            skillId = skillId,
            subSlotId = subSlotId,
            buffSlotId = buffSkillSlot.id,
            book = book,
            dadManager = dadManager)
}

// BUFFSKILL:
//------------------------------------------------------------------------------------------------------------

class BuffSKillController(

    private val skillId: SkillId,
    private val subSlotId: SubSkillSlotId,
    private val buffSlotId: BuffSkillSlotId,
    private val book: PlayerSkillBook,
    val dadManager: DragAndDropManager,

): StatController
{
    override fun upgrade(stat: StatId, numUpgrades: NumUpgrades) =

        book.upgrade(skillId, subSlotId, buffSlotId, stat, numUpgrades)
}

interface StatController {

    fun upgrade(stat: StatId, numUpgrades: NumUpgrades): Unit?
}
