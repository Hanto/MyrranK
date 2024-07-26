package com.myrran.model.spells.spell

import com.myrran.model.skills.custom.skill.Skill

enum class SkillType(val builder: (skill: Skill) -> Spell) {

    BOLT( builder = { skill: Skill -> SpellBolt(skill) } )
}
