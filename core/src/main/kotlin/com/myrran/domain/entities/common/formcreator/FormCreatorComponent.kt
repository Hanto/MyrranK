package com.myrran.domain.entities.common.formcreator

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.entities.common.collisioner.Collisionable
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.entities.common.effectable.Effectable
import com.myrran.domain.entities.common.vulnerable.Damage
import com.myrran.domain.entities.common.vulnerable.DamageType
import com.myrran.domain.entities.common.vulnerable.HP
import com.myrran.domain.entities.common.vulnerable.Vulnerable
import com.myrran.domain.events.FormSpellCastedEvent
import com.myrran.domain.misc.constants.SpellConstants.Companion.DIRECT_DAMAGE
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.form.CollisionType
import com.myrran.domain.skills.created.form.CollisionType.*
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.infraestructure.eventbus.EventDispatcher

class FormCreatorComponent(

    val eventDispatcher: EventDispatcher

): FormCreator
{
    // FORM CREATION:
    //--------------------------------------------------------------------------------------------------------

    override fun createForm(skillForm: FormSkill, collisionable: Collisionable) {

        when (skillForm.collisionType) {

            ON_EVERY_COLLISION_POINT -> createFormForEveryCollison(skillForm, collisionable)
            ON_SINGLE_COLLISION_POINT -> createFormAtTheCenter(skillForm, collisionable)
        }
    }

    private fun createFormForEveryCollison(skillForm: FormSkill, collisionable: Collisionable) {

        collisionable.retrieveCollisions().forEach {

            eventDispatcher.sendEvent(
                FormSpellCastedEvent(
                formSkill = skillForm,
                origin = it.pointOfCollision,
                direction = it.direction )
            )
        }
    }

    private fun createFormAtTheCenter(skillForm: FormSkill, collisionable: Collisionable) {

        eventDispatcher.sendEvent(
            FormSpellCastedEvent(
            formSkill = skillForm,
            origin = PositionMeters(collisionable.position.x, collisionable.position.y),
            direction = collisionable.getLinearVelocity() )
        )
    }
}
