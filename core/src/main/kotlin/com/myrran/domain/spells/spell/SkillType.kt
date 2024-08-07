package com.myrran.domain.spells.spell

import com.myrran.domain.skills.created.skill.Skill

enum class SkillType(val builder: (skill: Skill) -> Spell) {

    BOLT( builder = { skill: Skill -> SpellBolt(skill) } )
}
