package com.myrran.domain.spells

import com.myrran.domain.events.BuffSkillChangedEvent
import com.myrran.domain.events.BuffSkillRemovedEvent
import com.myrran.domain.events.BuffSkillStatUpgradedEvent
import com.myrran.domain.events.SkillEvent
import com.myrran.domain.events.SkillStatUpgradedEvent
import com.myrran.domain.events.SubSkillChangedEvent
import com.myrran.domain.events.SubSkillRemovedEvent
import com.myrran.domain.events.SubSkillStatUpgradedEvent
import com.myrran.domain.skills.custom.BuffSkill
import com.myrran.domain.skills.custom.SubSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.utils.Quantity
import com.myrran.domain.utils.QuantityMap
import com.myrran.domain.utils.observer.JavaObservable
import com.myrran.domain.utils.observer.Observable
import com.myrran.infraestructure.learned.LearnedRepository
import com.myrran.infraestructure.skill.SkillRepository
import com.myrran.infraestructure.skilltemplate.SkillTemplateRepository

data class SpellBook(

    private val skillTemplateRepository: SkillTemplateRepository,
    private val learnedRepository: LearnedRepository,
    val createdSkillsRepository: SkillRepository,
    private val observable: Observable<SkillEvent> = JavaObservable()

): Observable<SkillEvent> by observable
{
    private val learnedSkillTemplates: QuantityMap<SkillTemplateId> = learnedRepository.findAllLearnedSkillTemplates()
    private val learnedSubSkillsTemplates: QuantityMap<SubSkillTemplateId> = learnedRepository.findAllLearnedSubSkillTemplates()
    private val learnedBuffSkillsTemplates: QuantityMap<BuffSkillTemplateId> = learnedRepository.findAllLearnedBuffSkillTemplates()

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun learn(templateId: SkillTemplateId) {

        if (skillTemplateRepository.exists(templateId)) {

            learnedSkillTemplates.add(templateId)
            learnedRepository.saveLearnedSkills(learnedSkillTemplates)
        }
    }

    fun learn(templateId: SubSkillTemplateId) {

        if (skillTemplateRepository.exists(templateId)) {

            learnedSubSkillsTemplates.add(templateId)
            learnedRepository.saveLearnedSubSkills(learnedSubSkillsTemplates)
        }
    }

    fun learn(templateId: BuffSkillTemplateId) {

        if (skillTemplateRepository.exists(templateId)) {

            learnedBuffSkillsTemplates.add(templateId)
            learnedRepository.saveLearnedBuffSkills(learnedBuffSkillsTemplates)
        }
    }

    fun learnedBuffSkillTemplates(): Collection<QuantityItem<BuffSkillTemplate>> =

        skillTemplateRepository.findAllBuffSkillTemplates()
            .filter { learnedBuffSkillsTemplates.contains(it.id) }
            .map { QuantityItem(it, learnedBuffSkillsTemplates[it.id]!!) }

    // ADD:
    //--------------------------------------------------------------------------------------------------------

    fun addSkill(skillTemplateId: SkillTemplateId) {

        val skillTemplate = skillTemplateRepository.findBy(skillTemplateId)!!

        if (learnedSkillTemplates.isAvailable(skillTemplateId)) {

            learnedSkillTemplates.borrow(skillTemplate.id)
            val skill = skillTemplate.toSkill()

            createdSkillsRepository.save(skill)
            learnedRepository.saveLearnedSkills(learnedSkillTemplates)
        }
    }

    fun setSubSkillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, subSkillTemplateId: SubSkillTemplateId) {

        val skill = createdSkillsRepository.findBy(skillId)!!
        val subSkillTemplate = skillTemplateRepository.findBy(subSkillTemplateId)!!

        if (skill.isSubSkillOpenedBy(subSkillSlotId, subSkillTemplate) &&
            learnedSubSkillsTemplates.isAvailable(subSkillTemplateId)) {

            learnedSubSkillsTemplates.borrow(subSkillTemplateId)
            removeSubSkill(skillId, subSkillSlotId)
            val subSkill = subSkillTemplate.toSubSkill()
            skill.setSubSkill(subSkillSlotId, subSkill)

            createdSkillsRepository.save(skill)
            learnedRepository.saveLearnedSubSkills(learnedSubSkillsTemplates)
            notify(SubSkillChangedEvent(skillId, subSkillSlotId, subSkill))
        }
    }

    fun setBuffSKillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplateId: BuffSkillTemplateId) {

        val skill = createdSkillsRepository.findBy(skillId)!!
        val buffSkillTemplate = skillTemplateRepository.findBy(buffSkillTemplateId)!!
        val buffSkill = buffSkillTemplate.toBuffSkill()

        if (skill.isBuffSkillOpenedBy(subSkillSlotId, buffSkillSlotId, buffSkillTemplate) &&
            learnedBuffSkillsTemplates.isAvailable(buffSkillTemplateId)) {

            learnedBuffSkillsTemplates.borrow(buffSkillTemplateId)
            removeBuffSkill(skillId, subSkillSlotId, buffSkillSlotId)
            skill.setBuffSkill(subSkillSlotId, buffSkillSlotId, buffSkill)

            createdSkillsRepository.save(skill)
            learnedRepository.saveLearnedBuffSkills(learnedBuffSkillsTemplates)
            notify(BuffSkillChangedEvent(skillId, subSkillSlotId, buffSkillSlotId, buffSkill))
        }
    }

    // REMOVE:
    //--------------------------------------------------------------------------------------------------------

    fun removeSkill(skillId: SkillId) =

        createdSkillsRepository.findBy(skillId)?.also { skill ->

            val subBuffSKills = skill.removeAllSubSkills()

            subBuffSKills.forEach {

                when(it) {

                    is SubSkill -> learnedSubSkillsTemplates.returnBack(it.templateId)
                    is BuffSkill -> learnedBuffSkillsTemplates.returnBack(it.templateId)
                }
            }
            learnedSkillTemplates.returnBack(skill.templateId)

            if (subBuffSKills.any { it is SubSkill })
                learnedRepository.saveLearnedSubSkills(learnedSubSkillsTemplates)
            if (subBuffSKills.any { it is BuffSkill })
                learnedRepository.saveLearnedBuffSkills(learnedBuffSkillsTemplates)
            if (subBuffSKills.isNotEmpty())
                learnedRepository.saveLearnedSkills(learnedSkillTemplates)
        }

    fun removeSubSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId) =

        createdSkillsRepository.findBy(skillId)?.also { skill ->

            val subBuffSkills = skill.removeSubSkill(subSkillSlotId)

            subBuffSkills.forEach {

                when(it) {

                    is SubSkill -> learnedSubSkillsTemplates.returnBack(it.templateId)
                    is BuffSkill -> learnedBuffSkillsTemplates.returnBack(it.templateId)
                }
            }

            if (subBuffSkills.any { it is SubSkill })
                learnedRepository.saveLearnedSubSkills(learnedSubSkillsTemplates)
            if (subBuffSkills.any { it is BuffSkill })
                learnedRepository.saveLearnedBuffSkills(learnedBuffSkillsTemplates)
            if (subBuffSkills.isNotEmpty()) {

                createdSkillsRepository.save(skill)
                notify(SubSkillRemovedEvent(skillId, subBuffSkills))
            }
        }

    fun removeBuffSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId) =

        createdSkillsRepository.findBy(skillId)?.also { skill ->

            val buffSkill = skill.removeBuffSkill(subSkillSlotId, buffSkillSlotId)

            buffSkill?.also {

                learnedBuffSkillsTemplates.returnBack(it.templateId)

                learnedRepository.saveLearnedBuffSkills(learnedBuffSkillsTemplates)
                createdSkillsRepository.save(skill)
                notify(BuffSkillRemovedEvent(skillId, buffSkill))
            }
        }

    // IS OPENED:
    //--------------------------------------------------------------------------------------------------------

    fun isBuffSkillOpenedBy(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplateId: BuffSkillTemplateId): Boolean =

        createdSkillsRepository.findBy(skillId)
            ?.let { skill -> skillTemplateRepository.findBy(buffSkillTemplateId)
                ?.let { buffTemplate -> skill.isBuffSkillOpenedBy(subSkillSlotId, buffSkillSlotId, buffTemplate) } } ?: false

    fun isSubSkillOpenedBy(skillId: SkillId, subSkillSlotId: SubSkillSlotId, subSkillTemplateId: SubSkillTemplateId): Boolean =

        createdSkillsRepository.findBy(skillId)
            ?.let { skill -> skillTemplateRepository.findBy(subSkillTemplateId)
                ?.let { subSkillTemplate -> skill.isSubSkillOpenedBy(subSkillSlotId, subSkillTemplate) } } ?: false


    // UPGRADE:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(skillId: SkillId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = createdSkillsRepository.findBy(skillId)

        skill?.upgrade(statId, upgradeBy)
            ?.also { createdSkillsRepository.save(skill) }
            ?.also { notify(SkillStatUpgradedEvent(skillId, statId, upgradeBy)) }
    }

    fun upgrade(skillId: SkillId, subSkillSlotId: SubSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = createdSkillsRepository.findBy(skillId)

        skill?.upgrade(subSkillSlotId, statId, upgradeBy)
            ?.also { createdSkillsRepository.save(skill) }
            ?.also { notify(SubSkillStatUpgradedEvent(skillId, subSkillSlotId, statId, upgradeBy)) }
    }

    fun upgrade(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = createdSkillsRepository.findBy(skillId)

        skill?.upgrade(subSkillSlotId, buffSkillSlotId, statId, upgradeBy)
            ?.also { createdSkillsRepository.save(skill) }
            ?.also { notify(BuffSkillStatUpgradedEvent(skillId, subSkillSlotId, buffSkillSlotId, statId, upgradeBy)) }
    }
}

data class QuantityItem<T>(
    val item: T,
    val quantity: Quantity
)
