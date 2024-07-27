package com.myrran.domain.skills.book

import com.myrran.domain.skills.skills.buffskill.BuffSkill
import com.myrran.domain.skills.skills.buffskill.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.skills.buffskill.BuffSkillSlotId
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.domain.skills.skills.skill.SkillId
import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId
import com.myrran.domain.skills.templates.skills.BuffSkillTemplate
import com.myrran.domain.skills.templates.skills.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skills.SkillTemplate
import com.myrran.domain.skills.templates.skills.SkillTemplateId
import com.myrran.domain.skills.templates.skills.SubSkillTemplate
import com.myrran.domain.skills.templates.skills.SubSkillTemplateId

data class SkillBook(

    val skillTemplates: Map<SkillTemplateId, SkillTemplate>,
    val subSkillTemplates: Map<SubSkillTemplateId, SubSkillTemplate>,
    val buffSkillTemplates: Map<BuffSkillTemplateId, BuffSkillTemplate>,
    val learnedSkills: QuantityMap<SkillTemplateId>,
    val learnedSubSkills: QuantityMap<SubSkillTemplateId>,
    val learnedBuffSkills: QuantityMap<BuffSkillTemplateId>,
    val createdSkills: MutableMap<SkillId, Skill>,
)
{
    // MAIN:
    //--------------------------------------------------------------------------------------------------------

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

    private fun removeSubSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId) =

        when (val subSkill = createdSkills[skillId]!!.removeSubSkill(subSkillSlotId)) {

            NoSubSkill -> Unit
            is SubSkill -> {

                learnedSubSkills.returnBack(subSkill.templateId)
                subSkill.getBuffSkills().forEach { learnedBuffSkills.returnBack(it.templateId) }
            }
        }

    private fun removeBuffSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId) =

        when (val buffSkill = createdSkills[skillId]!!.removeBuffSkill(subSkillSlotId, buffSkillSlotId)) {

            NoBuffSkill -> Unit
            is BuffSkill -> learnedBuffSkills.returnBack(buffSkill.templateId)
        }
}
