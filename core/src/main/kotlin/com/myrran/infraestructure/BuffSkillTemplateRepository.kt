package com.myrran.infraestructure

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.utils.DeSerializer
import com.myrran.infraestructure.adapters.SkillTemplateAdapter

class BuffSkillTemplateRepository(

    private val skillTemplateAdapter: SkillTemplateAdapter,
    private val deSerializer: DeSerializer
)
{
    private val buffSkillTemplates: Map<BuffSkillTemplateId, BuffSkillTemplate>

    init {

        val json = Gdx.files.internal("${SubSkillTemplateRepository.CONFIG_FOLDER}BuffSkillTemplates.json").readString()
        val entities = deSerializer.deserialize(json, Array<BuffSKillTemplateEntity>::class.java ).toList()
        buffSkillTemplates = entities.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id }
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findAll(): Collection<BuffSkillTemplate> =

        buffSkillTemplates.values

    fun findById(id: BuffSkillTemplateId): BuffSkillTemplate? =

        buffSkillTemplates[id]
}
