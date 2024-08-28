package com.myrran.domain.entities.mob.spells.spell.formcreator

import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.collisioner.Collisioner
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.skills.created.form.FormSkill

interface FormCreator {

    fun<CorporealCollisioner> createForm(caster: Entity, spell: CorporealCollisioner, skillForm: FormSkill) where CorporealCollisioner: Corporeal, CorporealCollisioner: Collisioner
}
