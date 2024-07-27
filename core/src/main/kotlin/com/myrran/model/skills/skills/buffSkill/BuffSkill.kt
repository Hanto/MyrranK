package com.myrran.model.skills.skills.buffSkill

import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.stat.StatsI
import com.myrran.model.skills.templates.LockTypes
import com.myrran.model.skills.templates.skills.BuffSkillTemplateId
import com.myrran.model.spells.buffs.Buff
import com.myrran.model.spells.buffs.BuffType

data class BuffSkill(

    val id: BuffSkillId,
    val templateId: BuffSkillTemplateId,
    val type: BuffType,
    val name: BuffSkillName,
    val stats: Stats,
    val keys: Collection<LockTypes>

): BuffSkillSlotContent, StatsI by stats
{
    fun createBuff(): Buff =

        type.builder.invoke(this)
}
