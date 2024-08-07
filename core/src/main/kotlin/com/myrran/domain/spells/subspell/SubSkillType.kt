package com.myrran.domain.spells.subspell

import com.myrran.domain.skills.created.SubSkill

enum class SubSkillType(val builder: (subSkill: SubSkill) -> SubSpell) {

    EXPLOSION( builder = { subSkill: SubSkill -> Explosion(subSkill) } )
}
