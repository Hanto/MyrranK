package com.myrran.domain.spells.spell

import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.stat.StatId

class SpellBolt(

    val skill: Skill

): Spell
{
    val speed = skill.getStat(StatId("SPEED"))
    val size = skill.getStat(StatId("SIZE"))


    fun onCollision() {

        val impactFormSkill = skill.getFormSkill(FormSkillSlotId("IMPACT"))

        if (impactFormSkill is FormSkill)
        {
            impactFormSkill.createForm()
        }
    }

}
