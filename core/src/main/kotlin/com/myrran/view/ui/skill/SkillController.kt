package com.myrran.view.ui.skill

import com.myrran.domain.skills.book.PlayerSkillBook
import com.myrran.domain.skills.skills.buff.BuffSkillSlot
import com.myrran.domain.skills.skills.buff.BuffSkillSlotId
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.domain.skills.skills.skill.SkillId
import com.myrran.domain.skills.skills.stat.NumUpgrades
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.subskill.SubSkillSlot
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId

class SkillController(

    book: PlayerSkillBook
)
{
    val skillUpgrade: (SkillId) -> (StatId, NumUpgrades) -> Unit =
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

    fun toSubSkillController(skill: Skill, subSkillSlot: SubSkillSlot): SubSkillController =

        SubSkillController(
            subSkillUpgrade = subSkillUpgrade.invoke(skill.id).invoke(subSkillSlot.id),
            buffSkillUpgrade = buffSkillUpgrade.invoke(skill.id).invoke(subSkillSlot.id))
}

class SubSkillController(

    val subSkillUpgrade: (StatId, NumUpgrades) -> Unit,
    val buffSkillUpgrade: (BuffSkillSlotId) -> (StatId, NumUpgrades) -> Unit,
)
{
    fun toBuffSkillController(buffSkillSlot: BuffSkillSlot): BuffSKillController =

        BuffSKillController(
            buffSkillUpgrade = buffSkillUpgrade.invoke(buffSkillSlot.id))
}

class BuffSKillController(

    val buffSkillUpgrade: (StatId, NumUpgrades) -> Unit
)
