package com.myrran.view.ui.skills.controller

import com.myrran.domain.skills.book.PlayerSkillBook
import com.myrran.domain.skills.skills.buff.BuffSkillSlot
import com.myrran.domain.skills.skills.buff.BuffSkillSlotId
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.domain.skills.skills.skill.SkillId
import com.myrran.domain.skills.skills.stat.NumUpgrades
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.subskill.SubSkillSlot
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId

// SKILL:
//------------------------------------------------------------------------------------------------------------

class SkillController(

    book: PlayerSkillBook
)
{
    private val skillUpgrade: (SkillId) -> (StatId, NumUpgrades) -> Unit =
        { skillId -> {
            statId, numUpgrades ->
                book.upgrade(skillId, statId, numUpgrades) } }

    private val subSkillUpgrade: (SkillId) -> (SubSkillSlotId) -> (StatId, NumUpgrades) -> Unit =
        { skillId -> {
            subSkillSlotId -> {
                statId, numUpgrades ->
                    book.upgrade(skillId, subSkillSlotId, statId, numUpgrades) } } }

    private val buffSkillUpgrade: (SkillId) -> (SubSkillSlotId) -> (BuffSkillSlotId) -> (StatId, NumUpgrades) -> Unit =
        { skillId -> {
            subSkillSlotId -> {
                buffSkillSlotId -> {
                    statId, numUpgrades -> book.upgrade(skillId, subSkillSlotId, buffSkillSlotId, statId, numUpgrades) } } } }

    fun toStatController(skill: Skill) =

        StatController(
            upgrade = skillUpgrade.invoke(skill.id))

    fun toSubSkillController(skill: Skill, subSkillSlot: SubSkillSlot): SubSkillController =

        SubSkillController(
            subSkillUpgrade = subSkillUpgrade.invoke(skill.id).invoke(subSkillSlot.id),
            buffSkillUpgrade = buffSkillUpgrade.invoke(skill.id).invoke(subSkillSlot.id))
}

// SUBSKILL:
//------------------------------------------------------------------------------------------------------------

class SubSkillController(

    val subSkillUpgrade: (StatId, NumUpgrades) -> Unit,
    private val buffSkillUpgrade: (BuffSkillSlotId) -> (StatId, NumUpgrades) -> Unit,
)
{
    fun toStatController(): StatController =

        StatController(
            upgrade = subSkillUpgrade)

    fun toBuffSkillController(buffSkillSlot: BuffSkillSlot): BuffSKillController =

        BuffSKillController(
            buffSkillUpgrade = buffSkillUpgrade.invoke(buffSkillSlot.id))
}

// BUFFSKILL:
//------------------------------------------------------------------------------------------------------------

class BuffSKillController(

    val buffSkillUpgrade: (StatId, NumUpgrades) -> Unit
)
{
    fun toStatController(): StatController =

        StatController(
            upgrade = buffSkillUpgrade)
}

class StatController(

    val upgrade: (StatId, NumUpgrades) -> Unit
)
