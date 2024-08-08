package com.myrran.domain.skills.created.form

import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillSlot
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.effect.EffectSkillSlots
import com.myrran.domain.skills.created.skill.SkillFormEffect
import com.myrran.domain.skills.created.skill.SkillsRemoved
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.Stats
import com.myrran.domain.skills.created.stat.StatsI
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.lock.LockType
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.domain.skills.templates.form.FormTemplateId
import com.myrran.domain.spells.form.Form
import com.myrran.domain.spells.form.FormSkillType

data class FormSkill(

    val id: FormSkillId,
    val templateId: FormTemplateId,
    val type: FormSkillType,
    val name: FormSkillName,
    val stats: Stats,
    val slots: EffectSkillSlots,
    val keys: Collection<LockType>

): FormSkillSlotContent, StatsI by stats, SkillFormEffect
{
    fun createForm(): Form =

        type.builder.invoke(this.copy())

    // EFFECTSKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun getEffectSkillSlots(): Collection<EffectSkillSlot> =

        slots.getEffectSkillSlots()

    fun getEffectSkills(): Collection<EffectSkill> =

        slots.getEffectSkills()

    fun getEffectSkill(effectSkillSlotId: EffectSkillSlotId): EffectSkill? =

        slots.getEffectSkill(effectSkillSlotId)

    fun isOpenedBy(effectSkillSlotId: EffectSkillSlotId, effectTemplate: EffectTemplate): Boolean =

        slots.getEffectSkills().none { it.templateId == effectTemplate.id } &&
        slots.isEffectSkillSlotOpenedBy(effectSkillSlotId, effectTemplate)

    fun removeEffectSkillFrom(effectSkillSlotId: EffectSkillSlotId): SkillsRemoved =

        slots.removeEffectSkillFrom(effectSkillSlotId)

    fun removeAllEffectSkills(): SkillsRemoved =

        slots.removeAllEffectSkills()

    fun setEffectSkill(effectSkillSlotId: EffectSkillSlotId, effectSkill: EffectSkill) =

        slots.setEffectSkill(effectSkillSlotId, effectSkill)

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(effectSkillSlotId: EffectSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        slots.upgrade(effectSkillSlotId, statId, upgradeBy)

    fun totalCost(): UpgradeCost =

        stats.statCost() + slots.totalCost()

}

sealed interface FormSkillSlotContent {

    data object NoFormSkill: FormSkillSlotContent
}
