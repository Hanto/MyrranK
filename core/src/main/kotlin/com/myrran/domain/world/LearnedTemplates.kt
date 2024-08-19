package com.myrran.domain.world

import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.skill.Skill
import com.myrran.domain.skills.created.skill.SkillsRemoved
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.domain.skills.templates.effect.EffectTemplateId
import com.myrran.domain.skills.templates.form.FormTemplate
import com.myrran.domain.skills.templates.form.FormTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.infraestructure.repositories.learnedskilltemplate.LearnedSkillTemplateRepository
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateRepository

class LearnedTemplates(

    private val learnedRepository: LearnedSkillTemplateRepository,
    private val templateRepository: SkillTemplateRepository
)
{
    fun learnedSkillTemplates(): Collection<Quantity<SkillTemplate>> =

        learnedRepository.findAllSkillTemplates()
            .map { it.toQuantityOf(templateRepository.findBy(it.value)!!) }

    fun learnedFormTemplates(): Collection<Quantity<FormTemplate>> =

        learnedRepository.findAllFormTemplates()
            .map { it.toQuantityOf(templateRepository.findBy(it.value)!!) }

    fun learnedEffectTemplates(): Collection<Quantity<EffectTemplate>> =

        learnedRepository.findAllEffectTemplates()
            .map { it.toQuantityOf(templateRepository.findBy(it.value)!!) }

    // LEARN:
    //--------------------------------------------------------------------------------------------------------

    fun learn(id: SkillTemplateId) {

        if (templateRepository.exists(id)) {

            val quantity = learnedRepository.findBy(id).increaseAvailableAndTotal()
            learnedRepository.saveSkill(quantity)
        }
    }

    fun learn(id: FormTemplateId) {

        if (templateRepository.exists(id)) {

            val quantity = learnedRepository.findBy(id).increaseAvailableAndTotal()
            learnedRepository.saveForm(quantity)
        }
    }

    fun learn(id: EffectTemplateId) {

        if (templateRepository.exists(id)) {

            val quantity = learnedRepository.findBy(id).increaseAvailableAndTotal()
            learnedRepository.saveEffect(quantity)
        }
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findBy(id: SkillTemplateId): Quantity<SkillTemplate> {

        val quantity = learnedRepository.findBy(id)
        val template = templateRepository.findBy(id)!!

        return quantity.toQuantityOf(template)
    }

    fun findBy(id: FormTemplateId): Quantity<FormTemplate> {

        val quantity = learnedRepository.findBy(id)
        val template = templateRepository.findBy(id)!!

        return quantity.toQuantityOf(template)
    }

    fun findBy(id: EffectTemplateId): Quantity<EffectTemplate> {

        val quantity = learnedRepository.findBy(id)
        val template = templateRepository.findBy(id)!!

        return quantity.toQuantityOf(template)
    }

    // DECREASE AND SAVE:
    //--------------------------------------------------------------------------------------------------------

    fun decreaseAndSaveSkill(template: Quantity<SkillTemplate>) {

        val newQuantity = learnedRepository.findBy(template.value.id).decreaseAvailable()
        learnedRepository.saveSkill(newQuantity)
    }

    fun decreaseAndSaveForm(template: Quantity<FormTemplate>) {

        val newQuantity = learnedRepository.findBy(template.value.id).decreaseAvailable()
        learnedRepository.saveForm(newQuantity)
    }

    fun decreaseAndSaveEffect(template: Quantity<EffectTemplate>) {

        val newQuantity = learnedRepository.findBy(template.value.id).decreaseAvailable()
        learnedRepository.saveEffect(newQuantity)
    }

    // INCREASE AND SAVE:
    //--------------------------------------------------------------------------------------------------------

    fun increaseAndSave(skill: Skill) {

        val newQuantity = learnedRepository.findBy(skill.templateId).increaseAvailable()
        learnedRepository.saveSkill(newQuantity)
    }

    fun increaseAndSave(removed: SkillsRemoved) {

        increaseAndSaveForms(removed.removedForms)
        increaseAndSaveEffects(removed.removedEffects)
    }

    private fun increaseAndSaveForms(forms: Collection<FormSkill>) {

        if (forms.isNotEmpty()) {

            val newQuantities = forms.map { learnedRepository.findBy(it.templateId).increaseAvailable() }
            learnedRepository.saveForms(newQuantities)
        }
    }

    private fun increaseAndSaveEffects(effects: Collection<EffectSkill>) {

        if (effects.isNotEmpty()) {

            val newQuantities = effects.map { learnedRepository.findBy(it.templateId).increaseAvailable() }
            learnedRepository.saveEffects(newQuantities)
        }
    }
}
