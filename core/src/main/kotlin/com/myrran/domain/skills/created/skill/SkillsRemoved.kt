package com.myrran.domain.skills.created.skill

import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.form.FormSkill

data class SkillsRemoved(

    val removedForms: Collection<FormSkill> = emptyList(),
    val removedEffects: Collection<EffectSkill> = emptyList()
){
    constructor(form: FormSkill, removedEffects: Collection<EffectSkill>): this(listOf(form), removedEffects)

    operator fun plus(other: SkillsRemoved): SkillsRemoved =

        SkillsRemoved(
            removedForms = removedForms + other.removedForms,
            removedEffects = removedEffects + other.removedEffects)

    fun isNotEmpty(): Boolean =

        removedForms.isNotEmpty() && removedEffects.isNotEmpty()
}

