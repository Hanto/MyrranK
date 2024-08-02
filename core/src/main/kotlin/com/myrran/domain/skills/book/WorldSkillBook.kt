package com.myrran.domain.skills.book

import com.myrran.domain.skills.custom.buff.BuffSkill
import com.myrran.domain.skills.custom.skill.Skill
import com.myrran.domain.skills.custom.subskill.SubSkill
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId

data class WorldSkillBook(

    private val skillTemplates: Map<SkillTemplateId, SkillTemplate>,
    private val subSkillTemplates: Map<SubSkillTemplateId, SubSkillTemplate>,
    private val buffSkillTemplates: Map<BuffSkillTemplateId, BuffSkillTemplate>,

)
{
    fun skillTemplates(): Collection<SkillTemplate> = skillTemplates.values
    fun subSkillTemplates(): Collection<SubSkillTemplate> = subSkillTemplates.values
    fun buffSkillTemplates(): Collection<BuffSkillTemplate> = buffSkillTemplates.values

    fun createSkill(id: SkillTemplateId): Skill =

        skillTemplates[id]!!.toSkill()

    fun createSubSkill(id: SubSkillTemplateId): SubSkill =

        subSkillTemplates[id]!!.toSubSkill()

    fun createBuffSkill(id: BuffSkillTemplateId): BuffSkill =

        buffSkillTemplates[id]!!.toBuffSkill()
}
