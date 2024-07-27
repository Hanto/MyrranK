package com.myrran.domain.skills.skills.buffskill

import com.myrran.domain.skills.stat.Stats
import com.myrran.domain.skills.stat.StatsI
import com.myrran.domain.skills.templates.LockTypes
import com.myrran.domain.skills.templates.skills.BuffSkillTemplateId
import com.myrran.domain.spells.buffspell.Buff
import com.myrran.domain.spells.buffspell.BuffType

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

        type.builder.invoke(this.copy())
}
