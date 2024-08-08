package com.myrran.domain.skills.created.skill

import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.form.FormSkill

data class SkillsRemoved(

    val removedForms: Collection<FormSkill> = emptyList(),
    val removedEffects: Collection<EffectSkill> = emptyList()
){
    constructor(effectSkill: EffectSkill):
        this(emptyList<FormSkill>(), listOf(effectSkill))

    operator fun plus(other: SkillsRemoved): SkillsRemoved =

        SkillsRemoved(
            removedForms = removedForms + other.removedForms,
            removedEffects = removedEffects + other.removedEffects)

    operator fun plus(other: FormSkill): SkillsRemoved =

        SkillsRemoved(
            removedForms = removedForms + other,
            removedEffects = removedEffects
        )

    operator fun plus(other: EffectSkill): SkillsRemoved =

        SkillsRemoved(
            removedForms = removedForms,
            removedEffects = removedEffects + other
        )

    fun isNotEmpty(): Boolean =

        removedForms.isNotEmpty() || removedEffects.isNotEmpty()
}
