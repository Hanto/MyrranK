package com.myrran.domain.spells.spell

import com.myrran.domain.mob.Mob
import com.myrran.domain.mob.MobId
import com.myrran.domain.mob.Movable
import com.myrran.domain.mob.steerable.SteeringComponent
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.stat.StatId

class SpellBolt(

    override val id: MobId,
    val skill: Skill,
    private val movable: SteeringComponent

): Movable by movable, Mob, Spell
{
    val speed = skill.getStat(StatId("SPEED"))


    fun onCollision() {

        val impactFormSkill = skill.getFormSkill(FormSkillSlotId("IMPACT"))

        if (impactFormSkill is FormSkill)
        {

            val form = impactFormSkill.createForm()

        }
    }
}
