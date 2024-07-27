package com.myrran.model.spells.buffs

import com.myrran.model.skills.skills.buffSkill.BuffSkill

enum class BuffType(val builder: (buffSkill: BuffSkill) -> Buff)
{
    FIRE( builder = { buffSkill ->  FireDot(buffSkill) } )
}
