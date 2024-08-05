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
    private val learnedSkillTemplates: QuantityMap<SkillTemplateId> = learnedRepository.findAllLearnedSkillTemplates()
    private val learnedSubSkillsTemplates: QuantityMap<SubSkillTemplateId> = learnedRepository.findAllLearnedSubSkillTemplates()
    private val learnedBuffSkillsTemplates: QuantityMap<BuffSkillTemplateId> = learnedRepository.findAllLearnedBuffSkillTemplates()

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun learn(templateId: SkillTemplateId) {

        learnedSkillTemplates.add(templateId)
        learnedRepository.save(learnedSkillTemplates)
    }

    fun learn(templateId: SubSkillTemplateId) {

        learnedSubSkillsTemplates.add(templateId)
        learnedRepository.save(learnedSubSkillsTemplates)
    }

    fun learn(templateId: BuffSkillTemplateId) {

        learnedBuffSkillsTemplates.add(templateId)
        learnedRepository.save(learnedBuffSkillsTemplates)
    }

    // ADD:
    //--------------------------------------------------------------------------------------------------------

    fun addSkill(skillTemplateId: SkillTemplateId): Skill {

        val skillTemplate = skillTemplateRepository.findBy(skillTemplateId)!!
        val skill = skillTemplate.toSkill()

        learnedSkillTemplates.borrow(skillTemplate.id)

        createdSkillsRepository.save(skill)
        learnedRepository.save(learnedSkillTemplates)

        return skill
    }

    fun addSubSkillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, subSkillTemplateId: SubSkillTemplateId) {

        val skill = createdSkillsRepository.findBy(skillId)!!
        val subSkillTemplate = skillTemplateRepository.findBy(subSkillTemplateId)!!
        val subSkill = subSkillTemplate.toSubSkill()

        if (skill.isSubSkillOpenedBy(subSkillSlotId, subSkillTemplate) && learnedSubSkillsTemplates.isAvailable(subSkillTemplateId)) {

            learnedSubSkillsTemplates.borrow(subSkillTemplateId)
            removeSubSkill(skillId, subSkillSlotId)
            skill.setSubSkill(subSkillSlotId, subSkill)

            createdSkillsRepository.save(skill)
            learnedRepository.save(learnedSubSkillsTemplates)
        }
    }

    fun addBuffSKillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplateId: BuffSkillTemplateId) {

        val skill = createdSkillsRepository.findBy(skillId)!!
        val buffSkillTemplate = skillTemplateRepository.findBy(buffSkillTemplateId)!!
        val buffSkill = buffSkillTemplate.toBuffSkill()

        if (skill.isBuffSkillOpenedBy(subSkillSlotId, buffSkillSlotId, buffSkillTemplate) && learnedBuffSkillsTemplates.isAvailable(buffSkillTemplateId)) {

            learnedBuffSkillsTemplates.borrow(buffSkillTemplateId)
            removeBuffSkill(skillId, subSkillSlotId, buffSkillSlotId)
            skill.setBuffSkill(subSkillSlotId, buffSkillSlotId, buffSkill)

            createdSkillsRepository.save(skill)
            learnedRepository.save(learnedBuffSkillsTemplates)
        }
    }

    // REMOVE:
    //--------------------------------------------------------------------------------------------------------

    private fun removeSkill(skillId: SkillId) {

        createdSkillsRepository.findBy(skillId)
            ?.also {

                learnedSkillTemplates.returnBack(it.templateId)

                createdSkillsRepository.save(it)
                learnedRepository.save(learnedSkillTemplates)
            }
    }

    private fun removeSubSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId) {

        createdSkillsRepository.findBy(skillId)?.removeSubSkill(subSkillSlotId)
            ?.ifIs(SubSkill::class)?.also {

            learnedSubSkillsTemplates.returnBack(it.templateId)
            it.getBuffSkills().forEach { buffSkill -> learnedBuffSkillsTemplates.returnBack(buffSkill.templateId) }
        }
    }

    private fun removeBuffSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId) {

        createdSkillsRepository.findBy(skillId)?.removeBuffSkill(subSkillSlotId, buffSkillSlotId)
            ?.ifIs(BuffSkill::class)?.also {

            learnedBuffSkillsTemplates.returnBack(it.templateId)
        }
    }

    // IS OPENED:
    //--------------------------------------------------------------------------------------------------------


    fun isBuffSkillOpenedBy(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkillTemplateId: BuffSkillTemplateId): Boolean =

        createdSkillsRepository.findBy(skillId)
            ?.let { skill -> skillTemplateRepository.findBy(buffSkillTemplateId)
                ?.let { buffTemplate -> skill.isBuffSkillOpenedBy(subSkillSlotId, buffSkillSlotId, buffTemplate) } } ?: false


    // UPGRADE:
    //--------------------------------------------------------------------------------------------------------

    fun upgrade(skillId: SkillId, statId: StatId, upgradeBy: NumUpgrades) =

        createdSkillsRepository.findBy(skillId)?.upgrade(statId, upgradeBy)

    fun upgrade(skillId: SkillId, subSkillSlotId: SubSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        createdSkillsRepository.findBy(skillId)?.upgrade(subSkillSlotId, statId, upgradeBy)

    fun upgrade(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, statId: StatId, upgradeBy: NumUpgrades) =

        createdSkillsRepository.findBy(skillId)?.upgrade(subSkillSlotId, buffSkillSlotId, statId, upgradeBy)

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T>): T? =

        if (this is T) this else null
}
