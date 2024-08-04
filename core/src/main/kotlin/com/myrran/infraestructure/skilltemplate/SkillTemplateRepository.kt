package com.myrran.infraestructure.skilltemplate

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.utils.DeSerializer

class SkillTemplateRepository(

    private val skillTemplateAdapter: SkillTemplateAdapter,
    private val deSerializer: DeSerializer
)
{
    private val skillTemplates: Map<SkillTemplateId, SkillTemplate>
    private val subSkillTemplates: Map<SubSkillTemplateId, SubSkillTemplate>
    private val buffSkillTemplates: Map<BuffSkillTemplateId, BuffSkillTemplate>

    companion object {
        const val CONFIG_FOLDER = "config/"
    }

    init {

        skillTemplates = loadSkillTemplates().associateBy { it.id }
        subSkillTemplates = loadSubSkillTemplates().associateBy { it.id }
        buffSkillTemplates = loadBuffSkillTemplates().associateBy { it.id }
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findSkillTemplateById(id: SkillTemplateId): SkillTemplate? =

        skillTemplates[id]

    fun findSkillTemplates(): Collection<SkillTemplate> =

        skillTemplates.values

    fun findBySubSkillTemplateById(id: SubSkillTemplateId): SubSkillTemplate? =

        subSkillTemplates[id]

    fun findSubSkillTemplates(): Collection<SubSkillTemplate> =

        subSkillTemplates.values

    fun findByBuffSkillTemplatesById(id: BuffSkillTemplateId): BuffSkillTemplate? =

        buffSkillTemplates[id]

    fun findBuffSkillTemplates(): Collection<BuffSkillTemplate> =

        buffSkillTemplates.values

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun loadSkillTemplates(): Collection<SkillTemplate> {

        val skillJson = Gdx.files.internal("${CONFIG_FOLDER}SkillTemplates.json").readString()
        val skillEntities = deSerializer.deserialize(skillJson, Array<SkillTemplateEntity>::class.java ).toList()
        return skillEntities.map { skillTemplateAdapter.toDomain(it) }
    }

    private fun loadSubSkillTemplates(): Collection<SubSkillTemplate> {

        val subSkillJson = Gdx.files.internal("${CONFIG_FOLDER}SubSkillTemplates.json").readString()
        val subSkillEntities = deSerializer.deserialize(subSkillJson, Array<SubSkillTemplateEntity>::class.java ).toList()
        return subSkillEntities.map { skillTemplateAdapter.toDomain(it) }
    }

    private fun loadBuffSkillTemplates(): Collection<BuffSkillTemplate> {

        val buffSkillJson = Gdx.files.internal("${CONFIG_FOLDER}BuffSkillTemplates.json").readString()
        val buffSkillEntities = deSerializer.deserialize(buffSkillJson, Array<BuffSKillTemplateEntity>::class.java ).toList()
        return buffSkillEntities.map { skillTemplateAdapter.toDomain(it) }
    }
}
