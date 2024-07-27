package com.myrran.model.skills.skills.buffSkill

import com.myrran.model.skills.stat.Stats
import com.myrran.model.skills.stat.StatsI
import com.myrran.model.spells.buffs.Buff
import com.myrran.model.spells.buffs.BuffType

data class BuffSkill(

    val id: BuffSkillId,
    val type: BuffType,
    val name: BuffSkillName,
    val stats: Stats

): BuffSkillSlotContent, StatsI by stats
{
    fun createBuff(): Buff =

        type.builder.invoke(this)
}
