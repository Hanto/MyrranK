package com.myrran.domain.mobs.spells.spell

import com.badlogic.gdx.physics.box2d.World
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.stat.StatId

class SpellConstants {

    companion object {

        val COOLDOWN = StatId("COOLDOWN")
        val SPEED = StatId("SPEED")
        val SIZE = StatId("SIZE")
        val RANGE = StatId("RANGE")


        val IMPACT_SLOT = FormSkillSlotId("IMPACT:1")
    }
}

typealias WorldBox2D = World
