package com.myrran.domain.spells.subspell

import com.myrran.domain.skills.custom.subskill.SubSkill

enum class SubSkillType(val builder: (subSkill: SubSkill) -> SubSpell) {

    EXPLOSION( builder = { subSkill: SubSkill -> Explosion(subSkill) } )
}
