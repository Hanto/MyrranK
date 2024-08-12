package com.myrran.domain.spells.spell

import com.myrran.domain.mob.MobFactory
import com.myrran.domain.skills.created.skill.Skill

enum class SkillType(val builder: (factory: MobFactory, skill: Skill) -> Spell) {

    BOLT( builder = { factory, skill -> factory.createSpellBolt(skill) } )
}
