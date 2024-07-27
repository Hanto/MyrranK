package com.myrran.domain.spells.buffspell

import com.myrran.domain.skills.skills.buffskill.BuffSkill

enum class BuffType(val builder: (buffSkill: BuffSkill) -> Buff)
{
    FIRE( builder = { buffSkill ->  FireDot(buffSkill) } )
}
