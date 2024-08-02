package com.myrran.domain.skills.custom.buff

import com.myrran.domain.skills.custom.stat.Stats
import com.myrran.domain.skills.custom.stat.StatsI
import com.myrran.domain.skills.templates.LockType
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.spells.buff.Buff
import com.myrran.domain.spells.buff.BuffType

data class BuffSkill(

    val id: BuffSkillId,
    val templateId: BuffSkillTemplateId,
    val type: BuffType,
    val name: BuffSkillName,
    val stats: Stats,
    val keys: Collection<LockType>

): BuffSkillSlotContent, StatsI by stats
{
    fun createBuff(): Buff =

        type.builder.invoke(this.copy())
}
