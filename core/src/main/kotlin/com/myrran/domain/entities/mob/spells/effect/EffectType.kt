package com.myrran.domain.entities.mob.spells.effect

import com.myrran.domain.skills.created.effect.EffectSkill

enum class EffectType(

    val build: (effectSkill: EffectSkill) -> Effect,
)
{
    DAMAGE( { DamageEffect(it) } ),
    DOT( { DamageEffect(it) }  ),
    BOMB( { DamageEffect(it) }  )
}
