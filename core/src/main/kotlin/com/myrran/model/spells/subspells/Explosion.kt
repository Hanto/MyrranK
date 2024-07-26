package com.myrran.model.spells.subspells

import com.myrran.model.skills.skills.subskill.SubSkill
import com.myrran.model.skills.stat.StatId

class Explosion(

    val subSkill: SubSkill

): SubSpell
{
    val radius = subSkill.getStat(StatId("RADIUS"))
    val damage = subSkill.getStat(StatId("DAMAGE"))

    fun onCollision() {

        val debuffs = subSkill.getBuffSkills().map { it.createDebuff() }
    }
}
