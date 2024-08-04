package com.myrran.infraestructure

import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplate
import com.myrran.domain.utils.DeSerializer
import com.myrran.infraestructure.adapters.SkillTemplateAdapter

class SkillTemplateRepository(

    private val skillTemplateAdapter: SkillTemplateAdapter,
    private val deSerializer: DeSerializer

)
{
    private val skillTemplates: Map<SkillTemplateId, SkillTemplate> = mutableMapOf()

    init {


    }

    private fun loadFromJson(): List<SubSkillTemplate> {

        TODO()
    }
}
