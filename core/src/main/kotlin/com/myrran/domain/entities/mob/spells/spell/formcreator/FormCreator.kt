package com.myrran.domain.entities.mob.spells.spell.formcreator

import com.myrran.domain.entities.common.collisioner.Collisioner
import com.myrran.domain.entities.common.corporeal.Corporeal
import com.myrran.domain.skills.created.form.FormSkill

interface FormCreator {

    fun<T> createForm(skillForm: FormSkill, spell: T) where T: Corporeal, T: Collisioner
}
