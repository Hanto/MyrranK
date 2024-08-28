package com.myrran.domain.entities.mob.spells.spell.formcreator

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.collisioner.Collisioner
import com.myrran.domain.entities.common.collisioner.ExactLocation
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.events.FormSpellCastedEvent
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.skills.created.form.CollisionType.ON_EVERY_COLLISION_POINT
import com.myrran.domain.skills.created.form.CollisionType.ON_SINGLE_COLLISION_POINT
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.infraestructure.eventbus.EventDispatcher

class FormCreatorComponent(

    val eventDispatcher: EventDispatcher

): FormCreator
{
    // FORM CREATION:
    //--------------------------------------------------------------------------------------------------------

    override fun <T> createForm(caster: Entity, spell: T, skillForm: FormSkill) where T: Corporeal, T: Collisioner {

        when (skillForm.collisionType) {

            ON_EVERY_COLLISION_POINT -> createFormForEveryCollison(caster, spell, skillForm)
            ON_SINGLE_COLLISION_POINT -> createFormAtTheCenter(caster, spell, skillForm)
        }
    }

    private fun createFormForEveryCollison(caster: Entity, spell: Collisioner, skillForm: FormSkill) {

        spell.retrieveCollisions().forEach {

            eventDispatcher.sendEvent(
                FormSpellCastedEvent(
                caster = caster,
                formSkill = skillForm,
                location = it.location)
            )
        }
    }

    private fun createFormAtTheCenter(caster: Entity, spell: Corporeal, skillForm: FormSkill) {

        eventDispatcher.sendEvent(
            FormSpellCastedEvent(
            caster = caster,
            formSkill = skillForm,
            location = ExactLocation(
                origin = PositionMeters(spell.position.x, spell.position.y),
                direction = spell.getLinearVelocity()))
        )
    }
}
