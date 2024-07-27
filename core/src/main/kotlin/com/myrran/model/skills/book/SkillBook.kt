package com.myrran.model.skills.book

import com.myrran.model.skills.skills.buffSkill.BuffSkill
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotContent.NoBuffSkill
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.skills.skill.Skill
import com.myrran.model.skills.skills.skill.SkillId
import com.myrran.model.skills.skills.subskill.SubSkill
import com.myrran.model.skills.skills.subskill.SubSkillSlotContent.NoSubSkill
import com.myrran.model.skills.skills.subskill.SubSkillSlotId
import com.myrran.model.skills.templates.skills.BuffSkillTemplate
import com.myrran.model.skills.templates.skills.BuffSkillTemplateId
import com.myrran.model.skills.templates.skills.SkillTemplate
import com.myrran.model.skills.templates.skills.SkillTemplateId
import com.myrran.model.skills.templates.skills.SubSkillTemplate
import com.myrran.model.skills.templates.skills.SubSkillTemplateId

data class SkillBook(

    val skillTemplates: Map<SkillTemplateId, SkillTemplate>,
    val subSkillTemplates: Map<SubSkillTemplateId, SubSkillTemplate>,
    val buffSkillTemplates: Map<BuffSkillTemplateId, BuffSkillTemplate>
)
{
    private val learnedSkills = QuantityMap<SkillTemplateId>()
    private val learnedSubSkills = QuantityMap<SubSkillTemplateId>()
    private val learnedBuffSkills = QuantityMap<BuffSkillTemplateId>()
    private val createdSkills = mutableMapOf<SkillId, Skill>()

    constructor(

        skillTemplates: List<SkillTemplate>,
        subSkillTemplates: List<SubSkillTemplate>,
        buffSkillTemplates: List<BuffSkillTemplate>

    ): this(
        skillTemplates = skillTemplates.associateBy { it.id },
        subSkillTemplates = subSkillTemplates.associateBy { it.id },
        buffSkillTemplates = buffSkillTemplates.associateBy { it.id }
    )

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun learn(templateId: SkillTemplateId) = learnedSkills.add(templateId)
    fun learn(templateId: SubSkillTemplateId) = learnedSubSkills.add(templateId)
    fun learn(templateId: BuffSkillTemplateId) = learnedBuffSkills.add(templateId)

    fun createSkill(templateId: SkillTemplateId) {

        val skill = skillTemplates[templateId]!!.toSkill()

        learnedSkills.borrow(templateId)

        createdSkills[skill.id] = skill
    }

    fun addSubSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId, templateId: SubSkillTemplateId) {

        val skill = createdSkills[skillId]!!
        val subSkillTemplate = subSkillTemplates[templateId]!!

        removeSubSkill(skillId, subSkillSlotId)
        learnedSubSkills.borrow(templateId)

        skill.setSubSkill(subSkillSlotId, subSkillTemplate)
    }

    fun addBuffSKill(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, templateId: BuffSkillTemplateId) {

        val skill = createdSkills[skillId]!!
        val buffSKillTemplate = buffSkillTemplates[templateId]!!

        removeBuffSkill(skillId, subSkillSlotId, buffSkillSlotId)
        learnedBuffSkills.borrow(templateId)

        skill.setBuffSkill(subSkillSlotId, buffSkillSlotId, buffSKillTemplate)
    }

    private fun removeSkill(skillId: SkillId) {

        val skill = createdSkills.remove(skillId)

        learnedSkills.add(skill!!.templateId)
    }

    private fun removeSubSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId) =

        when (val subSkill = createdSkills[skillId]!!.removeSubSkill(subSkillSlotId)) {

            NoSubSkill -> Unit
            is SubSkill -> {

                learnedSubSkills.add(subSkill.templateId)
                subSkill.getBuffSkills().forEach { learnedBuffSkills.add(it.templateId) }
            }
        }

    private fun removeBuffSkill(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId) =

        when (val buffSkill = createdSkills[skillId]!!.removeBuffSkill(subSkillSlotId, buffSkillSlotId)) {

            NoBuffSkill -> Unit
            is BuffSkill -> learnedBuffSkills.add(buffSkill.templateId)
        }
}
