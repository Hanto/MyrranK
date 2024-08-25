package com.myrran.domain.misc.constants

import com.badlogic.gdx.physics.box2d.World
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.stat.StatId

class SpellConstants {

    companion object {

        val COOLDOWN = StatId("COOLDOWN")
        val SPEED = StatId("SPEED")
        val SIZE = StatId("SIZE")
        val EXPIRATION = StatId("EXPIRATION")
        val PENETRATION = StatId("PENETRATION")
        val DIRECT_DAMAGE = StatId("DIRECT_DAMAGE")
        val MAX_STACKS = StatId("MAX_STACKS")
        val DAMAGE_PER_TICK = StatId("DAMAGE_PER_TICK")

        val IMPACT_SLOT = FormSkillSlotId("IMPACT:1")
    }
}
typealias WorldBox2D = World
