package com.myrran.domain.spells.spell

import com.myrran.domain.skills.custom.Skill
import com.myrran.domain.skills.custom.SubSkill
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId

class SpellBolt(

    val skill: Skill

): Spell
{
    val speed = skill.getStat(StatId("SPEED"))
    val size = skill.getStat(StatId("SIZE"))


    fun onCollision() {

        val impactSubSkill = skill.getSubSkill(SubSkillSlotId("IMPACT"))

        if (impactSubSkill is SubSkill)
        {
            impactSubSkill.createSpell()
        }
    }

}
