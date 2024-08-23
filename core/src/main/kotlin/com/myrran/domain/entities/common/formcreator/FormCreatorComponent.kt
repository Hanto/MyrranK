package com.myrran.domain.entities.common.formcreator

import com.myrran.domain.entities.common.collisioner.Collisioner
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.events.FormSpellCastedEvent
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.skills.created.form.CollisionType.*
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.infraestructure.eventbus.EventDispatcher

class FormCreatorComponent(

    val eventDispatcher: EventDispatcher

): FormCreator
{
    // FORM CREATION:
    //--------------------------------------------------------------------------------------------------------

    override fun <T> createForm(skillForm: FormSkill, spell: T) where T: Corporeal, T: Collisioner {

        when (skillForm.collisionType) {

            ON_EVERY_COLLISION_POINT -> createFormForEveryCollison(skillForm, spell)
            ON_SINGLE_COLLISION_POINT -> createFormAtTheCenter(skillForm, spell)
        }
    }

    private fun createFormForEveryCollison(skillForm: FormSkill, spell: Collisioner) {

        spell.retrieveCollisions().forEach {

            eventDispatcher.sendEvent(
                FormSpellCastedEvent(
                formSkill = skillForm,
                origin = it.pointOfCollision,
                direction = it.direction )
            )
        }
    }

    private fun createFormAtTheCenter(skillForm: FormSkill, spell: Corporeal) {

        eventDispatcher.sendEvent(
            FormSpellCastedEvent(
            formSkill = skillForm,
            origin = PositionMeters(spell.position.x, spell.position.y),
            direction = spell.getLinearVelocity() )
        )
    }
}
