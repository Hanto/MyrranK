package com.myrran.infraestructure.skilltemplate

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.templates.BuffSkillTemplate
import com.myrran.domain.skills.templates.SkillTemplate
import com.myrran.domain.skills.templates.SubSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
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

    fun findBy(id: SkillTemplateId): SkillTemplate? =

        skillTemplates[id]

    fun findBy(id: SubSkillTemplateId): SubSkillTemplate? =

        subSkillTemplates[id]

    fun findBy(id: BuffSkillTemplateId): BuffSkillTemplate? =

        buffSkillTemplates[id]

    fun findAllSkillTemplates(): Collection<SkillTemplate> =

        skillTemplates.values

    fun findAllSubSkillTemplates(): Collection<SubSkillTemplate> =

        subSkillTemplates.values

    fun findAllBuffSkillTemplates(): Collection<BuffSkillTemplate> =

        buffSkillTemplates.values

    fun exists(id: SkillTemplateId): Boolean =

        skillTemplates.containsKey(id)

    fun exists(id: SubSkillTemplateId): Boolean =

        subSkillTemplates.containsKey(id)

    fun exists(id: BuffSkillTemplateId): Boolean =

        buffSkillTemplates.containsKey(id)

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
