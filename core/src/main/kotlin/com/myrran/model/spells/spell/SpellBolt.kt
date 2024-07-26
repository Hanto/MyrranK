package com.myrran.model.spells.spell

import com.myrran.model.skills.skills.skill.Skill
import com.myrran.model.skills.skills.subskill.SubSkillSlotId
import com.myrran.model.skills.stat.StatId

class SpellBolt(

    val skill: Skill

): Spell
{
    val speed = skill.getStat(StatId("SPEED"))
    val size = skill.getStat(StatId("SIZE"))


    fun onCollision() {

        val impactSubSkill = skill.getSubSkill(SubSkillSlotId("IMPACT"))
        val impactSpell = impactSubSkill?.createSpell()

    }

}
