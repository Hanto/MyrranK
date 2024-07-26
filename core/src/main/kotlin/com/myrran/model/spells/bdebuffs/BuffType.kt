package com.myrran.model.spells.bdebuffs

import com.myrran.model.skills.skills.bdebuff.BuffSkill

enum class BuffType(val builder: (buffSkill: BuffSkill) -> BDebuff)
{
    FIRE( builder = { buffSkill ->  FireDot(buffSkill) } )
}
