package com.myrran.domain.spells.buff

import com.myrran.domain.skills.created.BuffSkill

enum class BuffType(val builder: (buffSkill: BuffSkill) -> Buff)
{
    FIRE( builder = { buffSkill ->  FireDot(buffSkill) } ),
    BOMB( builder = { buffSkill ->  FireDot(buffSkill) } )
}
