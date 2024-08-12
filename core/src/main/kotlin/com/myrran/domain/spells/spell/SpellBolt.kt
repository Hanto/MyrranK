package com.myrran.domain.spells.spell

import com.badlogic.gdx.math.Vector2
import com.myrran.application.World
import com.myrran.domain.mob.Mob
import com.myrran.domain.mob.MobId
import com.myrran.domain.mob.Movable
import com.myrran.domain.mob.steerable.SteeringComponent
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.stat.StatId
import ktx.math.minus

class SpellBolt(

    override val id: MobId,
    val skill: Skill,
    private val movable: SteeringComponent,
    origin: Vector2,
    target: Vector2,

): Movable by movable, Mob, Spell
{
    var timeToLife = 2f
    override var toBeRemoved = false

    init {

        position = origin
        val direction = target.minus(position).nor()
        val speed = skill.getStat(StatId("SPEED"))!!.totalBonus()

        setLinearVelocity(direction, speed.value)
    }


    fun onCollision() {

        val impactFormSkill = skill.getFormSkill(FormSkillSlotId("IMPACT"))

        if (impactFormSkill is FormSkill)
        {

            val form = impactFormSkill.createForm()

        }
    }

    override fun act(deltaTime: Float, world: World) {

        timeToLife -= deltaTime

        if(timeToLife <0) {

            toBeRemoved = true
        }
    }

}
