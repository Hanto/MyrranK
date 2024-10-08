package com.myrran.domain.world

import com.myrran.domain.events.EffectSkillChangedEvent
import com.myrran.domain.events.EffectSkillRemovedEvent
import com.myrran.domain.events.EffectSkillStatUpgradedEvent
import com.myrran.domain.events.FormSkillChangedEvent
import com.myrran.domain.events.FormSkillRemovedEvent
import com.myrran.domain.events.FormSkillStatUpgradedEvent
import com.myrran.domain.events.SkillCreatedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.SkillRemovedEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.misc.Quantity
import com.myrran.domain.misc.observer.JavaObservable
import com.myrran.domain.misc.observer.Observable
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.skills.created.skill.SkillName
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.domain.skills.templates.effect.EffectTemplateId
import com.myrran.domain.skills.templates.form.FormTemplate
import com.myrran.domain.skills.templates.form.FormTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.infraestructure.repositories.skill.SkillRepository

data class SpellBook(

    private val created: SkillRepository,
    private val learned: LearnedTemplates,
    private val observable: Observable<SkillEvent> = JavaObservable()

): Observable<SkillEvent> by observable
{
    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun learn(id: SkillTemplateId) = learned.learn(id)
    fun learn(id: FormTemplateId) = learned.learn(id)
    fun learn(id: EffectTemplateId) = learned.learn(id)

    fun learnedSkillTemplates(): Collection<Quantity<SkillTemplate>> = learned.learnedSkillTemplates()
    fun learnedFormTemplates(): Collection<Quantity<FormTemplate>> = learned.learnedFormTemplates()
    fun learnedEffectTemplates(): Collection<Quantity<EffectTemplate>> = learned.learnedEffectTemplates()

    // PLAYER SKILLS:
    //--------------------------------------------------------------------------------------------------------

    fun findPlayerSkill(skillId: SkillId): Skill? =

        created.findBy(skillId)

    fun findAllPlayerSkills(): Collection<Skill> =

        created.findAll()

    // ADD:
    //--------------------------------------------------------------------------------------------------------

    fun changeName(skillId: SkillId, newName: SkillName) {

        val skill = created.findBy(skillId)!!
        skill.customName = newName

        created.save(skill)
    }

    fun addSkill(id: SkillTemplateId) {

        val skillTemplate = learned.findBy(id)

        if (skillTemplate.isAvailable()) {

            val skill = skillTemplate.value.toSkill()

            created.save(skill)
            learned.decreaseAndSaveSkill(skillTemplate)

            notify(SkillCreatedEvent(skill.id))
        }
    }

    fun setFormSkillTo(skillId: SkillId, formSkillSlotId: FormSkillSlotId, formTemplateId: FormTemplateId) {

        val skill = created.findBy(skillId)!!
        val formTemplate = learned.findBy(formTemplateId)

        if (skill.isFormSkillSlotOpenedBy(formSkillSlotId, formTemplate.value) && formTemplate.isAvailable())
        {
            val formSkill = formTemplate.value.toFormSkill()
            val removed = skill.setFormSkill(formSkillSlotId, formSkill)

            created.save(skill)
            learned.decreaseAndSaveForm(formTemplate)
            learned.increaseAndSave(removed)

            if (removed.isNotEmpty())
                notify(FormSkillRemovedEvent(skillId, formSkillSlotId))
            notify(FormSkillChangedEvent(skillId, formSkillSlotId))
        }
    }

    fun setEffectSkillTo(skillId: SkillId, formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId, effectTemplateId: EffectTemplateId) {

        val skill = created.findBy(skillId)!!
        val formTemplate = learned.findBy(effectTemplateId)

        if (skill.isEffectSkillSlotOpenedBy(formSkillSlotId, effectSkillSlotId, formTemplate.value) && formTemplate.isAvailable()) {

            val effectSkill = formTemplate.value.toEffectSkill()
            val removed = skill.setEffectSkill(formSkillSlotId, effectSkillSlotId, effectSkill)

            created.save(skill)
            learned.decreaseAndSaveEffect(formTemplate)
            learned.increaseAndSave(removed)

            if (removed.isNotEmpty())
                notify(EffectSkillRemovedEvent(skillId, formSkillSlotId, effectSkillSlotId))
            notify(EffectSkillChangedEvent(skillId, formSkillSlotId, effectSkillSlotId))
        }
    }

    // REMOVE:
    //--------------------------------------------------------------------------------------------------------

    fun removeSkill(skillId: SkillId) {

        val skill = created.findBy(skillId)!!

        val removed = skill.removeSkill()

        learned.increaseAndSave(skill)
        learned.increaseAndSave(removed)
        created.removeBy(skill.id)

        notify(SkillRemovedEvent(skillId))
    }

    fun removeFormSkillFrom(skillId: SkillId, formSkillSlotId: FormSkillSlotId) {

        val skill = created.findBy(skillId)!!

        val removed = skill.removeFormSkillFrom(formSkillSlotId)

        if (removed.isNotEmpty()) {

            created.save(skill)
            learned.increaseAndSave(removed)

            notify(FormSkillRemovedEvent(skillId, formSkillSlotId))
        }
    }

    fun removeEffectSkillFrom(skillId: SkillId, formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId) {

        val skill = created.findBy(skillId)!!

        val removed = skill.removeEffectSkillFrom(formSkillSlotId, effectSkillSlotId)

        if (removed.isNotEmpty())
        {
            created.save(skill)
            learned.increaseAndSave(removed)

            notify(EffectSkillRemovedEvent(skillId, formSkillSlotId, effectSkillSlotId))
        }
    }

    // IS OPENED:
    //--------------------------------------------------------------------------------------------------------

    fun isFormSkillSlotOpenedBy(skillId: SkillId, formSkillSlotId: FormSkillSlotId, formTemplateId: FormTemplateId): Boolean {

        val skill = created.findBy(skillId)!!
        val formTemplate = learned.findBy(formTemplateId)

        return skill.isFormSkillSlotOpenedBy(formSkillSlotId, formTemplate.value)
    }

    fun isEffectSkillSlotOpenedBy(skillId: SkillId, formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId, effectTemplateId: EffectTemplateId): Boolean {

        val skill = created.findBy(skillId)!!
        val effectTemplate = learned.findBy(effectTemplateId)

        return skill.isEffectSkillSlotOpenedBy(formSkillSlotId, effectSkillSlotId, effectTemplate.value)
    }

    // UPGRADE:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(skillId: SkillId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = created.findBy(skillId)!!

        skill.upgrade(statId, upgradeBy)
        created.save(skill)
        notify(SkillStatUpgradedEvent(skillId, statId))
    }

    fun upgrade(skillId: SkillId, formSkillSlotId: FormSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = created.findBy(skillId)!!

        skill.upgrade(formSkillSlotId, statId, upgradeBy)
        created.save(skill)
        notify(FormSkillStatUpgradedEvent(skillId, formSkillSlotId, statId))
    }

    fun upgrade(skillId: SkillId, formSkillSlotId: FormSkillSlotId, effectSkillSlotId: EffectSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = created.findBy(skillId)!!

        skill.upgrade(formSkillSlotId, effectSkillSlotId, statId, upgradeBy)
        created.save(skill)
        notify(EffectSkillStatUpgradedEvent(skillId, formSkillSlotId, effectSkillSlotId, statId))
    }
}
