package com.myrran.domain.mobs.spells.form

import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.stat.StatId

class Explosion(

    val formSkill: FormSkill

): Form
{
    val radius = formSkill.getStat(StatId("RADIUS"))
    val damage = formSkill.getStat(StatId("DAMAGE"))

    fun onCollision() {

        val effects = formSkill.getEffectSkills().map { it.createEffect() }
    }
}
