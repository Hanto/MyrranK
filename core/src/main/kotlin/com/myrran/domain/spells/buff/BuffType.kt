package com.myrran.domain.spells.buff

import com.myrran.domain.skills.custom.buff.BuffSkill

enum class BuffType(val builder: (buffSkill: BuffSkill) -> Buff)
{
    FIRE( builder = { buffSkill ->  FireDot(buffSkill) } )
}
