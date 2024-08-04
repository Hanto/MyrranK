package com.myrran.infraestructure

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.utils.DeSerializer
import com.myrran.infraestructure.adapters.SkillTemplateAdapter
import com.myrran.infraestructure.entities.SkillTemplateEntity

class SkillTemplateRepository(

    private val skillTemplateAdapter: SkillTemplateAdapter,
    private val deSerializer: DeSerializer

)
{
    private val skillTemplates: Map<SkillTemplateId, SkillTemplate>

    init {

        val json = Gdx.files.internal("${SubSkillTemplateRepository.CONFIG_FOLDER}SkillTemplates.json").readString()
        val entities = deSerializer.deserialize(json, Array<SkillTemplateEntity>::class.java ).toList()
        skillTemplates = entities.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id }
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findAll(): Collection<SkillTemplate> =

        skillTemplates.values

    fun findById(id: SkillTemplateId): SkillTemplate? =

        skillTemplates[id]
}
