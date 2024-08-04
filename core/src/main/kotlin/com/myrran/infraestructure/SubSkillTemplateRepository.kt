package com.myrran.infraestructure

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.templates.subskill.SubSkillTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.utils.DeSerializer
import com.myrran.infraestructure.adapters.SkillTemplateAdapter

class SubSkillTemplateRepository(

    private val skillTemplateAdapter: SkillTemplateAdapter,
    private val deSerializer: DeSerializer
)
{
    companion object {
        const val CONFIG_FOLDER = "config/"
    }

    private val subSkillTemplates: Map<SubSkillTemplateId, SubSkillTemplate>

    init {

        val json = Gdx.files.internal("${CONFIG_FOLDER}SubSkillTemplates.json").readString()
        val entities = deSerializer.deserialize(json, Array<SubSkillTemplateEntity>::class.java ).toList()
        subSkillTemplates = entities.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id }
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findAll(): Collection<SubSkillTemplate> =

        subSkillTemplates.values

    fun findById(id: SubSkillTemplateId): SubSkillTemplate? =

        subSkillTemplates[id]
}
