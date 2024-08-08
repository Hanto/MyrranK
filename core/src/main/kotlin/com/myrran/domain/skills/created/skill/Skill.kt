package com.myrran.domain.skills.created.skill

import com.myrran.domain.events.EffectSkillChangedEvent
import com.myrran.domain.events.EffectSkillRemovedEvent
import com.myrran.domain.events.EffectSkillStatUpgradedEvent
import com.myrran.domain.events.FormSkillChangedEvent
import com.myrran.domain.events.FormSkillRemovedEvent
import com.myrran.domain.events.FormSkillStatUpgradedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.misc.observer.JavaObservable
import com.myrran.domain.misc.observer.Observable
import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.form.FormSkillSlot
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.form.FormSkillSlots
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.stat.Stats
import com.myrran.domain.skills.created.stat.StatsI
import com.myrran.domain.skills.created.stat.UpgradeCost
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.domain.skills.templates.form.FormTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.spells.spell.SkillType

data class Skill(

    override val id: SkillId,
    val templateId: SkillTemplateId,
    val type: SkillType,
    val name: SkillName,
    var custonName: SkillName,
    private val stats: Stats,
    private val slots: FormSkillSlots,
    private val observable: Observable<SkillEvent> = JavaObservable()

): StatsI by stats, Observable<SkillEvent> by observable, Identifiable<SkillId>
{
    // FORMKILLS
    //--------------------------------------------------------------------------------------------------------

    fun getFormSkillSlots(): Collection<FormSkillSlot> =

        slots.getFormSkillSlots().map { it.copy() }

    fun getFormSkill(formSkillSlotId: FormSkillSlotId): FormSkill? =

        slots.getFormSkill(formSkillSlotId)?.copy()

    fun isFormSkillSlotOpenedBy(formSkillSlotId: FormSkillSlotId, formTemplate: FormTemplate): Boolean =

        slots.getFormSkillSlots().none { it.getFormSkill()?.templateId == formTemplate.id } &&
        slots.isFormSkillSlotOpenedBy(formSkillSlotId, formTemplate)

    fun removeFormSkillFrom(formSkillSlotId: FormSkillSlotId): SkillsRemoved =

        slots.removeFormSkillFrom(formSkillSlotId)
            .also { if (it.isNotEmpty()) notify(FormSkillRemovedEvent(id, it)) }

    fun removeAllFormSkills(): SkillsRemoved =

        slots.removeAllFormSkills()
            .also { if (it.isNotEmpty()) notify(FormSkillRemovedEvent(id, it)) }

    fun setFormSkill(formSkillSlotId: FormSkillSlotId, formSkill: FormSkill): SkillsRemoved =

        removeFormSkillFrom(formSkillSlotId)
            .also { slots.setFormSkill(formSkillSlotId, formSkill) }
            .also { notify(FormSkillChangedEvent(id, formSkillSlotId, formSkill)) }

    // EFFECTSKILL
    //--------------------------------------------------------------------------------------------------------

    fun getEffectSkill(formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId): EffectSkill? =

        slots.getEffectSkill(formSkillSlotId, effectSkillSlotId)?.copy()

    fun isEffectSkillSlotOpenedBy(formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId, effectTemplate: EffectTemplate): Boolean =

        slots.isEffectSkillSlotOpenedBy(formSkillSlotId, effectSkillSlotId, effectTemplate)

    fun removeEffectSkillFrom(formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId): EffectSkill? =

        slots.removeEffectSKillFrom(formSkillSlotId, effectSkillSlotId)?.copy()
            ?.also { notify(EffectSkillRemovedEvent(id, it)) }

    fun setEffectSkill(formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId, effectSkill: EffectSkill): EffectSkill? =

        removeEffectSkillFrom(formSkillSlotId, effectSkillSlotId)
            .also { slots.setEffectSkill(formSkillSlotId, effectSkillSlotId, effectSkill) }
            .also { notify(EffectSkillChangedEvent(id, formSkillSlotId, effectSkillSlotId, effectSkill)) }

    // UPGRADES:
    //--------------------------------------------------------------------------------------------------------

    override fun upgrade(statId: StatId, upgradeBy: NumUpgrades) =

        stats.upgrade(statId, upgradeBy)
            .also { notify(SkillStatUpgradedEvent(id, statId, upgradeBy)) }

    fun upgrade(formSkillSlotId: FormSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        slots.upgrade(formSkillSlotId, statId, upgradeBy)
            .also { notify(FormSkillStatUpgradedEvent(id, formSkillSlotId, statId, upgradeBy)) }

    fun upgrade(formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        slots.upgrade(formSkillSlotId, effectSkillSlotId, statId, upgradeBy)
            .also { notify(EffectSkillStatUpgradedEvent(id, formSkillSlotId, effectSkillSlotId, statId, upgradeBy)) }

    fun totalCost(): UpgradeCost =

        stats.statCost() + slots.totalCost()
}
