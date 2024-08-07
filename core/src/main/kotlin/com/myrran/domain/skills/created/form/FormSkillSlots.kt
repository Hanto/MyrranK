package com.myrran.domain.skills.created.form

import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.domain.skills.templates.form.FormTemplate

data class FormSkillSlots(

    private val bySlotId: Map<FormSkillSlotId, FormSkillSlot>,
)
{
    // FORM SKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getFormSkillSlots(): Collection<FormSkillSlot> =

        bySlotId.values

    fun getFormSkill(formSkillSlotId: FormSkillSlotId): FormSkill? =

        bySlotId[formSkillSlotId]?.getFormSkill()

    fun isFormSkillSlotOpenedBy(formSkillSlotId: FormSkillSlotId, formTemplate: FormTemplate): Boolean =

        bySlotId[formSkillSlotId]?.isFormSkillSlotOpenedBy(formTemplate) ?: false

    fun removeFormSkillFrom(formSkillSlotId: FormSkillSlotId): FormSkill? =

        bySlotId[formSkillSlotId]?.removeFormSkill()

    fun removeAllFormSkills(): Collection<FormSkill> =

        bySlotId.values.mapNotNull { removeFormSkillFrom(it.id) }

    fun setFormSkill(formSkillSlotId: FormSkillSlotId, formSkill: FormSkill) =

        bySlotId[formSkillSlotId]?.setFormSkill(formSkill)

    // EFFECT SKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getEffectSkill(formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId): EffectSkill? =

        bySlotId[formSkillSlotId]?.getEffectSkill(effectSkillSlotId)

    fun isEffectSkillSlotOpenedBy(formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId, effectTemplate: EffectTemplate): Boolean =

        bySlotId[formSkillSlotId]?.isEffectSkillSlotOpenedBy(effectSkillSlotId, effectTemplate) ?: false

    fun removeEffectSKillFrom(formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId): EffectSkill? =

        bySlotId[formSkillSlotId]?.removeEffectSkillFrom(effectSkillSlotId)

    fun removeAllEffectSkills(): Collection<EffectSkill> =

        bySlotId.values.flatMap { it.removeAllEffectSkills() }

    fun setEffectSkill(formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId, effectSkill: EffectSkill) =

        bySlotId[formSkillSlotId]?.setEffectSkill(effectSkillSlotId, effectSkill)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(slotId: FormSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        bySlotId[slotId]?.upgrade(statId, upgradeBy)

    fun upgrade(slotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        bySlotId[slotId]?.upgrade(effectSkillSlotId, statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        bySlotId.values
            .map { it.totalCost() }
            .reduce { acc, next -> acc.plus(next) }
}

