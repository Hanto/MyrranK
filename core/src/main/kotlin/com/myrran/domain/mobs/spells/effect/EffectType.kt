package com.myrran.domain.mobs.spells.effect

import com.myrran.domain.skills.created.effect.EffectSkill

enum class EffectType(val builder: (effectSkill: EffectSkill) -> Effect)
{
    FIRE( builder = { effectSkill ->  Dot(effectSkill) } ),
    BOMB( builder = { effectSkill ->  Dot(effectSkill) } )
}
