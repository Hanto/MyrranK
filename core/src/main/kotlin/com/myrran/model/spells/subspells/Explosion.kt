package com.myrran.model.spells.subspells

import com.myrran.model.skills.custom.skill.subskill.SubSkill
import com.myrran.model.skills.custom.stat.StatId

class Explosion(

    val subSkill: SubSkill

): SubSpell
{
    val radius = subSkill.getStat(StatId("RADIUS"))
    val damage = subSkill.getStat(StatId("DAMAGE"))

    fun onCollision() {

        val debuffs = subSkill.getSlots().map { it.createDebuff() }
    }
}
