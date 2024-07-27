package com.myrran.domain.spells.subspell

import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.stat.StatId

class Explosion(

    val subSkill: SubSkill

): SubSpell
{
    val radius = subSkill.getStat(StatId("RADIUS"))
    val damage = subSkill.getStat(StatId("DAMAGE"))

    fun onCollision() {

        val debuffs = subSkill.getBuffSkills().map { it.createBuff() }
    }
}
