package com.myrran.model.spells.spell

import com.myrran.model.skills.custom.skill.Skill
import com.myrran.model.skills.custom.skill.subskill.SubSkillSlotId
import com.myrran.model.skills.custom.stat.StatId

class SpellBolt(

    val skill: Skill

): Spell
{
    val speed = skill.getStat(StatId("SPEED"))
    val size = skill.getStat(StatId("SIZE"))


    fun onCollision() {

        val impactSubSkill = skill.getSlot(SubSkillSlotId("IMPACT"))
        val impactSpell = impactSubSkill?.createSpell()

    }

}
