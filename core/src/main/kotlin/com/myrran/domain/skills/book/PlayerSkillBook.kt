package com.myrran.domain.skills.book

import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.skill.Skill
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.subskill.SubSkill
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.utils.QuantityMap
import com.myrran.infraestructure.learned.LearnedRepository
import com.myrran.infraestructure.skill.SkillRepository
import com.myrran.infraestructure.skilltemplate.SkillTemplateRepository
import kotlin.reflect.KClass

data class PlayerSkillBook(

    private val skillTemplateRepository: SkillTemplateRepository,
    private val learnedRepository: LearnedRepository,
    val createdSkillsRepository: SkillRepository
)
{
    private val learnedSkillTemplates: QuantityMap<SkillTemplateId> = learnedRepository.findLearnedSkillTemplates()
    private val learnedSubSkillsTemplates: QuantityMap<SubSkillTemplateId> = learnedRepository.findLearnedSubSkillTemplates()
    private val learnedBuffSkillsTemplates: QuantityMap<BuffSkillTemplateId> = learnedRepository.findLearnedBuffSkillTemplates()

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun learn(templateId: SkillTemplateId) {

        learnedSkillTemplates.add(templateId)
        learnedRepository.saveLearnedSkillTemplates(learnedSkillTemplates)
    }

    fun learn(templateId: SubSkillTemplateId) {

        learnedSubSkillsTemplates.add(templateId)
        learnedRepository.saveLearnedSubSkillTemplates(learnedSubSkillsTemplates)
    }

    fun learn(templateId: BuffSkillTemplateId) {

        learnedBuffSkillsTemplates.add(templateId)
        learnedRepository.saveLearnedBuffSkillTemplates(learnedBuffSkillsTemplates)
    }

    // ADD:
    //--------------------------------------------------------------------------------------------------------

    fun addSkill(skillTemplateId: SkillTemplateId): Skill {

        val skillTemplate = skillTemplateRepository.findSkillTemplateById(skillTemplateId)!!
        val skill = skillTemplate.toSkill()

        learnedSkillTemplates.borrow(skillTemplate.id)

        createdSkillsRepository.saveSkill(skill)
        learnedRepository.saveLearnedSkillTemplates(learnedSkillTemplates)

        return skill
    }

    fun addSubSkillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, subSkillTemplateId: SubSkillTemplateId) {

        val skill = createdSkillsRepository.findSkillById(skillId)!!
        val subSkillTemplate = skillTemplateRepository.findBySubSkillTemplateById(subSkillTemplateId)!!
        val subSkill = subSkillTemplate.toSubSkill()

        if (skill.isSubSkillOpenedBy(subSkillSlotId, subSkillTemplate) && learnedSubSkillsTemplates.isAvailable(subSkillTemplateId)) {

            learnedSubSkillsTemplates.borrow(subSkillTemplateId)
            removeSubSkill(skillId, subSkillSlotId)
            skill.setSubSkill(subSkillSlotId, subSkill)

            createdSkillsRepository.saveSkill(skill)
            learnedRepository.saveLearnedSubSkillTemplates(learnedSubSkillsTemplates)
        }
    }

    fun addBuffSKillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplateId: BuffSkillTemplateId) {

        val skill = createdSkillsRepository.findSkillById(skillId)!!
        val buffSkillTemplate = skillTemplateRepository.findByBuffSkillTemplatesById(buffSkillTemplateId)!!
        val buffSkill = buffSkillTemplate.toBuffSkill()

        if (skill.isBuffSkillOpenedBy(subSkillSlotId, buffSkillSlotId, buffSkillTemplate) && learnedBuffSkillsTemplates.isAvailable(buffSkillTemplateId)) {

            learnedBuffSkillsTemplates.borrow(buffSkillTemplateId)
            removeBuffSkill(skillId, subSkillSlotId, buffSkillSlotId)
            skill.setBuffSkill(subSkillSlotId, buffSkillSlotId, buffSkill)

            createdSkillsRepository.saveSkill(skill)
            learnedRepository.saveLearnedBuffSkillTemplates(learnedBuffSkillsTemplates)
        }
    }

    // REMOVE:
    //--------------------------------------------------------------------------------------------------------

    private fun removeSkill(skillId: SkillId) {

        createdSkillsRepository.findSkillById(skillId)
            ?.also {

                learnedSkillTemplates.returnBack(it.templateId)

                createdSkillsRepository.saveSkill(it)
                learnedRepository.saveLearnedSkillTemplates(learnedSkillTemplates)
            }
    }

    private fun removeSubSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId) {

        createdSkillsRepository.findSkillById(skillId)?.removeSubSkill(subSkillSlotId)
            ?.ifIs(SubSkill::class)?.also {

            learnedSubSkillsTemplates.returnBack(it.templateId)
            it.getBuffSkills().forEach { buffSkill -> learnedBuffSkillsTemplates.returnBack(buffSkill.templateId) }
        }
    }

    private fun removeBuffSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId) {

        createdSkillsRepository.findSkillById(skillId)?.removeBuffSkill(subSkillSlotId, buffSkillSlotId)
            ?.ifIs(BuffSkill::class)?.also {

            learnedBuffSkillsTemplates.returnBack(it.templateId)
        }
    }

    // IS OPENED:
    //--------------------------------------------------------------------------------------------------------


    fun isBuffSkillOpenedBy(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplateId: BuffSkillTemplateId): Boolean =

        createdSkillsRepository.findSkillById(skillId)
            ?.let { skill -> skillTemplateRepository.findByBuffSkillTemplatesById(buffSkillTemplateId)
                ?.let { buffTemplate -> skill.isBuffSkillOpenedBy(subSkillSlotId, buffSkillSlotId, buffTemplate) } } ?: false


    // UPGRADE:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(skillId: SkillId, statId: StatId, upgradeBy: NumUpgrades) =

        createdSkillsRepository.findSkillById(skillId)?.upgrade(statId, upgradeBy)

    fun upgrade(skillId: SkillId, subSkillSlotId: SubSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        createdSkillsRepository.findSkillById(skillId)?.upgrade(subSkillSlotId, statId, upgradeBy)

    fun upgrade(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        createdSkillsRepository.findSkillById(skillId)?.upgrade(subSkillSlotId, buffSkillSlotId, statId, upgradeBy)

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T>): T? =

        if (this is T) this else null
}
