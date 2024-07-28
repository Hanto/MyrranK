package com.myrran.domain.skills.book

import com.myrran.domain.skills.skills.buff.BuffSkill
import com.myrran.domain.skills.skills.buff.BuffSkillSlotId
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.domain.skills.skills.skill.SkillId
import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.utils.QuantityMap
import kotlin.reflect.KClass

data class SkillBook(

    private val skillTemplates: Map<SkillTemplateId, SkillTemplate>,
    private val subSkillTemplates: Map<SubSkillTemplateId, SubSkillTemplate>,
    private val buffSkillTemplates: Map<BuffSkillTemplateId, BuffSkillTemplate>,
    private val learnedSkills: QuantityMap<SkillTemplateId>,
    private val learnedSubSkills: QuantityMap<SubSkillTemplateId>,
    private val learnedBuffSkills: QuantityMap<BuffSkillTemplateId>,
    private val createdSkills: MutableMap<SkillId, Skill>,
)
{
    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun skillTemplates(): Collection<SkillTemplate> = skillTemplates.values
    fun subSkillTemplates(): Collection<SubSkillTemplate> = subSkillTemplates.values
    fun buffSkillTemplates(): Collection<BuffSkillTemplate> = buffSkillTemplates.values
    fun createdSkills(): Collection<Skill> = createdSkills.values

    fun learn(templateId: SkillTemplateId) = learnedSkills.returnBack(templateId)
    fun learn(templateId: SubSkillTemplateId) = learnedSubSkills.returnBack(templateId)
    fun learn(templateId: BuffSkillTemplateId) = learnedBuffSkills.returnBack(templateId)

    fun createSkill(templateId: SkillTemplateId) {

        val skill = skillTemplates[templateId]!!.toSkill()

        learnedSkills.borrow(templateId)

        createdSkills[skill.id] = skill
    }

    fun addSubSkillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, templateId: SubSkillTemplateId) {

        val skill = createdSkills[skillId]!!
        val subSkill = subSkillTemplates[templateId]!!.toSubSkill()

        removeSubSkill(skillId, subSkillSlotId)
        learnedSubSkills.borrow(templateId)

        skill.setSubSkill(subSkillSlotId, subSkill)
    }

    fun addBuffSKillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, templateId: BuffSkillTemplateId) {

        val skill = createdSkills[skillId]!!
        val buffSkill = buffSkillTemplates[templateId]!!.toBuffSkill()

        removeBuffSkill(skillId, subSkillSlotId, buffSkillSlotId)
        learnedBuffSkills.borrow(templateId)

        skill.setBuffSkill(subSkillSlotId, buffSkillSlotId, buffSkill)
    }

    private fun removeSkill(skillId: SkillId) {

        val skill = createdSkills.remove(skillId)

        learnedSkills.returnBack(skill!!.templateId)
    }

    private fun removeSubSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId) {

        val subSkill = createdSkills[skillId]!!.removeSubSkill(subSkillSlotId)

        subSkill.ifIs(SubSkill::class)?.also {

            learnedSubSkills.returnBack(it.templateId)
            it.getBuffSkills().forEach { buffSkill -> learnedBuffSkills.returnBack(buffSkill.templateId) }
        }
    }

    private fun removeBuffSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId) {

        val buffSkill = createdSkills[skillId]!!.removeBuffSkill(subSkillSlotId, buffSkillSlotId)

        buffSkill.ifIs(BuffSkill::class)?.also {

            learnedBuffSkills.returnBack(it.templateId)
        }
    }

    private inline fun <reified T: Any> Any.ifIs(classz: KClass<T>): T? =

        if (this is T) this else null
}
