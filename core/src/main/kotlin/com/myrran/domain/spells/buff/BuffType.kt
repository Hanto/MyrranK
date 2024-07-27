package com.myrran.domain.spells.buff

import com.myrran.domain.skills.skills.buff.BuffSkill

enum class BuffType(val builder: (buffSkill: BuffSkill) -> Buff)
{
    FIRE( builder = { buffSkill ->  FireDot(buffSkill) } )
}
