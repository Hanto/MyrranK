package com.myrran.infraestructure

import com.myrran.domain.skills.book.SkillBook

class SpellBookAdapter(

    private val skillAdapter: SkillAdapter,
    private val skillTemplateAdapter: SkillTemplateAdapter
) {

    fun fromDomain(domain: SkillBook): SkillBookEntity =

        SkillBookEntity(
            skillTemplates = domain.skillTemplates().map { skillTemplateAdapter.fromDomain(it) },
            subSkillTemplates = domain.subSkillTemplates().map { skillTemplateAdapter.fromDomain(it) },
            buffSkillTemplates = domain.buffSkillTemplates().map { skillTemplateAdapter.fromDomain(it) },
            createdSkills = domain.createdSkills().map { skillAdapter.fromDomain(it) }
        )
}
