package com.myrran.domain.spells.form

import com.myrran.domain.skills.created.form.FormSkill

enum class FormSkillType(val builder: (formSkill: FormSkill) -> Form) {

    EXPLOSION( builder = { formSkill: FormSkill -> Explosion(formSkill) } )
}
