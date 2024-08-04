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

        val skillJson = Gdx.files.internal("${CONFIG_FOLDER}SkillTemplates.json").readString()
        val skillEntities = deSerializer.deserialize(skillJson, Array<SkillTemplateEntity>::class.java ).toList()
        skillTemplates = skillEntities.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id }

        val subSkillJson = Gdx.files.internal("${CONFIG_FOLDER}SubSkillTemplates.json").readString()
        val subSkillEntities = deSerializer.deserialize(subSkillJson, Array<SubSkillTemplateEntity>::class.java ).toList()
        subSkillTemplates = subSkillEntities.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id }

        val buffSkillJson = Gdx.files.internal("${CONFIG_FOLDER}BuffSkillTemplates.json").readString()
        val buffSkillEntities = deSerializer.deserialize(buffSkillJson, Array<BuffSKillTemplateEntity>::class.java ).toList()
        buffSkillTemplates = buffSkillEntities.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id }
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findSkillTemplates(): Collection<SkillTemplate> =

        skillTemplates.values

    fun findSkillTemplateById(id: SkillTemplateId): SkillTemplate? =

        skillTemplates[id]

    fun findSubSkillTemplates(): Collection<SubSkillTemplate> =

        subSkillTemplates.values

    fun findBySubSkillTemplateById(id: SubSkillTemplateId): SubSkillTemplate? =

        subSkillTemplates[id]

    fun findBuffSkillTemplates(): Collection<BuffSkillTemplate> =

        buffSkillTemplates.values

    fun findByBuffSkillTemplatesById(id: BuffSkillTemplateId): BuffSkillTemplate? =

        buffSkillTemplates[id]
}
