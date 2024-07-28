package com.myrran.infraestructure

import com.myrran.domain.skills.book.SkillBook
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.utils.QuantityMap

class SkillBookAdapter(

    private val skillAdapter: SkillAdapter,
    private val skillTemplateAdapter: SkillTemplateAdapter
) {

    fun fromDomain(domain: SkillBook): SkillBookEntity =

        SkillBookEntity(
            skillTemplates = domain.skillTemplates().map { skillTemplateAdapter.fromDomain(it) },
            subSkillTemplates = domain.subSkillTemplates().map { skillTemplateAdapter.fromDomain(it) },
            buffSkillTemplates = domain.buffSkillTemplates().map { skillTemplateAdapter.fromDomain(it) },
            learnedSkills = domain.learnedSkills().map { LearnedEntity(it.key.value, it.value) },
            learnedSubSKills = domain.learnedSubSKills().map { LearnedEntity(it.key.value, it.value) },
            learnedBuffSkills = domain.learnedBuffSkills().map { LearnedEntity(it.key.value, it.value) },
            createdSkills = domain.createdSkills().map { skillAdapter.fromDomain(it) }
        )

    fun toDomain(entity: SkillBookEntity): SkillBook =

        SkillBook(
            skillTemplates = entity.skillTemplates.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id },
            subSkillTemplates = entity.subSkillTemplates.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id },
            buffSkillTemplates = entity.buffSkillTemplates.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id },
            learnedSkills = QuantityMap(entity.learnedSkills.associate { SkillTemplateId(it.id) to it.quantity }.toMutableMap()),
            learnedSubSkills = QuantityMap(entity.learnedSubSKills.associate { SubSkillTemplateId(it.id) to it.quantity }.toMutableMap()),
            learnedBuffSkills = QuantityMap(entity.learnedBuffSkills.associate { BuffSkillTemplateId(it.id) to it.quantity }.toMutableMap()),
            createdSkills = entity.createdSkills.map { skillAdapter.toDomain(it) }.associateBy { it.id }.toMutableMap()
        )
}
