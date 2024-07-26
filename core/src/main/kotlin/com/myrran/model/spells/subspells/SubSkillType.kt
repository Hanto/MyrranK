package com.myrran.model.spells.subspells

import com.myrran.model.skills.custom.skill.subskill.SubSkill

enum class SubSkillType(val builder: (subSkill: SubSkill) -> SubSpell) {

    EXPLOSION( builder = { subSkill: SubSkill -> Explosion(subSkill) } )
}
