package com.myrran.domain.entities.common.formcreator

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.entities.common.collisioner.Collisionable
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.entities.common.vulnerable.DamageLocation
import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.form.FormSkill

interface FormCreator {

    fun createForm(skillForm: FormSkill, collisionable: Collisionable, direction: Vector2)
}
