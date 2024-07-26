package com.myrran.model.spells.bdebuffs

import com.myrran.model.skills.custom.skill.subskill.buff.BuffSkill

enum class BuffType(val builder: (buffSkill: BuffSkill) -> BDebuff)
{
    FIRE( builder = { buffSkill ->  FireDot(buffSkill) } )
}
