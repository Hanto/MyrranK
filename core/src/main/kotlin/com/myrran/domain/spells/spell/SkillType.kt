package com.myrran.domain.spells.spell

import com.myrran.domain.skills.custom.Skill

enum class SkillType(val builder: (skill: Skill) -> Spell) {

    BOLT( builder = { skill: Skill -> SpellBolt(skill) } )
}
