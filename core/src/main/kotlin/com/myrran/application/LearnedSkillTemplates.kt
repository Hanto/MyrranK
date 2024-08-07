package com.myrran.application

import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.created.BuffSkill
import com.myrran.domain.skills.created.Skill
import com.myrran.domain.skills.created.SubSkill
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.domain.skills.templates.SkillTemplate
import com.myrran.domain.skills.templates.SubSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.infraestructure.repositories.learnedskilltemplate.LearnedSkillTemplateRepository
import com.myrran.infraestructure.repositories.skilltemplate.SkillTemplateRepository

class LearnedSkillTemplates(

    private val learnedRepository: LearnedSkillTemplateRepository,
    private val templateRepository: SkillTemplateRepository
)
{
    fun learnedSkillTemplates(): Collection<Quantity<SkillTemplate>> =

        learnedRepository.findAllSkillTemplates()
            .map { it.toQuantityOf(templateRepository.findBy(it.value)!!) }

    fun learnedSubSkillTemplates(): Collection<Quantity<SubSkillTemplate>> =

        learnedRepository.findAllSubSkillTemplates()
            .map { it.toQuantityOf(templateRepository.findBy(it.value)!!) }

    fun learnedBuffSkillTemplates(): Collection<Quantity<BuffSkillTemplate>> =

        learnedRepository.findAllBuffSkillTemplates()
            .map { it.toQuantityOf(templateRepository.findBy(it.value)!!) }

    // LEARN:
    //--------------------------------------------------------------------------------------------------------

    fun learn(id: SkillTemplateId) {

        if (templateRepository.exists(id)) {

            val quantity = learnedRepository.findBy(id).increaseAvailableAndTotal()
            learnedRepository.saveSkill(quantity)
        }
    }

    fun learn(id: SubSkillTemplateId) {

        if (templateRepository.exists(id)) {

            val quantity = learnedRepository.findBy(id).increaseAvailableAndTotal()
            learnedRepository.saveSub(quantity)
        }
    }

    fun learn(id: BuffSkillTemplateId) {

        if (templateRepository.exists(id)) {

            val quantity = learnedRepository.findBy(id).increaseAvailableAndTotal()
            learnedRepository.saveBuff(quantity)
        }
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findBy(id: SkillTemplateId): Quantity<SkillTemplate> {

        val quantity = learnedRepository.findBy(id)
        val template = templateRepository.findBy(id)!!

        return quantity.toQuantityOf(template)
    }

    fun findBy(id: SubSkillTemplateId): Quantity<SubSkillTemplate> {

        val quantity = learnedRepository.findBy(id)
        val template = templateRepository.findBy(id)!!

        return quantity.toQuantityOf(template)
    }

    fun findBy(id: BuffSkillTemplateId): Quantity<BuffSkillTemplate> {

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

    fun decreaseAndSaveSub(template: Quantity<SubSkillTemplate>) {

        val newQuantity = learnedRepository.findBy(template.value.id).decreaseAvailable()
        learnedRepository.saveSub(newQuantity)
    }

    fun decreaseAndSaveBuff(template: Quantity<BuffSkillTemplate>) {

        val newQuantity = learnedRepository.findBy(template.value.id).decreaseAvailable()
        learnedRepository.saveBuff(newQuantity)
    }

    // INCREASE AND SAVE:
    //--------------------------------------------------------------------------------------------------------

    fun increaseAndSave(skill: Skill) {

        val newQuantity = learnedRepository.findBy(skill.templateId).increaseAvailable()
        learnedRepository.saveSkill(newQuantity)
    }

    fun increaseAndSave(skillTemplate: BuffSkillTemplateId) {

        val newQuantity = learnedRepository.findBy(skillTemplate).increaseAvailable()
        learnedRepository.saveBuff(newQuantity)
    }

    fun increaseAndSaveSubs(quantityList: Collection<SubSkill>) {

        if (quantityList.isNotEmpty()) {

            val newQuantities = quantityList.map { learnedRepository.findBy(it.templateId).increaseAvailable() }
            learnedRepository.saveSubs(newQuantities)
        }
    }

    fun increaseAndSaveBuffs(quantityList: Collection<BuffSkill>) {

        if (quantityList.isNotEmpty()) {

            val newQuantities = quantityList.map { learnedRepository.findBy(it.templateId).increaseAvailable() }
            learnedRepository.saveBuffs(newQuantities)
        }
    }
}
