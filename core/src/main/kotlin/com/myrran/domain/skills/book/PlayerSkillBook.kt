package com.myrran.domain.skills.book

import com.myrran.domain.skills.custom.BuffSkill
import com.myrran.domain.skills.custom.SubSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.utils.QuantityMap
import com.myrran.infraestructure.learned.LearnedRepository
import com.myrran.infraestructure.skill.SkillRepository
import com.myrran.infraestructure.skilltemplate.SkillTemplateRepository

data class PlayerSkillBook(

    private val skillTemplateRepository: SkillTemplateRepository,
    private val learnedRepository: LearnedRepository,
    val createdSkillsRepository: SkillRepository
)
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

    fun addSubSkillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, subSkillTemplateId: SubSkillTemplateId) {

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
        }
    }

    fun addBuffSKillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplateId: BuffSkillTemplateId) {

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

            createdSkillsRepository.save(skill)
        }

    fun removeBuffSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId) =

        createdSkillsRepository.findBy(skillId)?.also { skill ->

            skill.removeBuffSkill(subSkillSlotId, buffSkillSlotId)?.also {

                learnedBuffSkillsTemplates.returnBack(it.templateId)

                learnedRepository.saveLearnedBuffSkills(learnedBuffSkillsTemplates)
            }

            createdSkillsRepository.save(skill)
        }

    // IS OPENED:
    //--------------------------------------------------------------------------------------------------------

    fun isBuffSkillOpenedBy(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplateId: BuffSkillTemplateId): Boolean =

        createdSkillsRepository.findBy(skillId)
            ?.let { skill -> skillTemplateRepository.findBy(buffSkillTemplateId)
                ?.let { buffTemplate -> skill.isBuffSkillOpenedBy(subSkillSlotId, buffSkillSlotId, buffTemplate) } } ?: false


    // UPGRADE:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(skillId: SkillId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = createdSkillsRepository.findBy(skillId)

        skill?.upgrade(statId, upgradeBy)
            ?.also { createdSkillsRepository.save(skill) }
    }

    fun upgrade(skillId: SkillId, subSkillSlotId: SubSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = createdSkillsRepository.findBy(skillId)

        skill?.upgrade(subSkillSlotId, statId, upgradeBy)
            ?.also { createdSkillsRepository.save(skill) }
    }

    fun upgrade(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) {

        val skill = createdSkillsRepository.findBy(skillId)

        skill?.upgrade(subSkillSlotId, buffSkillSlotId, statId, upgradeBy)
            ?.also { createdSkillsRepository.save(skill) }
    }
}
