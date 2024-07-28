package com.myrran.domain.skills.book

import com.myrran.domain.skills.skills.buff.BuffSkill
import com.myrran.domain.skills.skills.buff.BuffSkillSlotId
import com.myrran.domain.skills.skills.skill.Skill
import com.myrran.domain.skills.skills.skill.SkillId
import com.myrran.domain.skills.skills.subskill.SubSkill
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.utils.QuantityMap
import kotlin.reflect.KClass

data class PlayerSkillBook(

    private val learnedSkills: QuantityMap<SkillTemplateId>,
    private val learnedSubSkills: QuantityMap<SubSkillTemplateId>,
    private val learnedBuffSkills: QuantityMap<BuffSkillTemplateId>,
    private val createdSkills: MutableMap<SkillId, Skill>,

)
{
    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun learnedSkills() = learnedSkills.entries
    fun learnedSubSKills() = learnedSubSkills.entries
    fun learnedBuffSkills() = learnedBuffSkills.entries
    fun createdSkills(): Collection<Skill> = createdSkills.values

    fun learn(templateId: SkillTemplateId) = learnedSkills.add(templateId)
    fun learn(templateId: SubSkillTemplateId) = learnedSubSkills.add(templateId)
    fun learn(templateId: BuffSkillTemplateId) = learnedBuffSkills.add(templateId)

    fun getSkill(skillId: SkillId): Skill = createdSkills[skillId]!!.copy()

    fun addSkill(skill: Skill) {

        learnedSkills.borrow(skill.templateId)

        createdSkills[skill.id] = skill
    }

    fun addSubSkillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, subSkill: SubSkill) {

        val skill = createdSkills[skillId]!!

        removeSubSkill(skillId, subSkillSlotId)
        learnedSubSkills.borrow(subSkill.templateId)

        skill.setSubSkill(subSkillSlotId, subSkill)
    }

    fun addBuffSKillTo(skillId: SkillId, subSkillSlotId: SubSkillSlotId, buffSkillSlotId: BuffSkillSlotId, buffSkill: BuffSkill) {

        val skill = createdSkills[skillId]!!

        removeBuffSkill(skillId, subSkillSlotId, buffSkillSlotId)
        learnedBuffSkills.borrow(buffSkill.templateId)

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
