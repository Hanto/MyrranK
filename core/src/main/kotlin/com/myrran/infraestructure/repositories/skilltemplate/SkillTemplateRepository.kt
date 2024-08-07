package com.myrran.infraestructure.repositories.skilltemplate

import com.badlogic.gdx.Gdx
import com.myrran.domain.misc.DeSerializer
import com.myrran.domain.skills.templates.effect.EffectTemplate
import com.myrran.domain.skills.templates.effect.EffectTemplateId
import com.myrran.domain.skills.templates.form.FormTemplate
import com.myrran.domain.skills.templates.form.FormTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplate
import com.myrran.domain.skills.templates.skill.SkillTemplateId

class SkillTemplateRepository(

    private val skillTemplateAdapter: SkillTemplateAdapter,
    private val deSerializer: DeSerializer
)
{
    private val skillTemplates: Map<SkillTemplateId, SkillTemplate>
    private val formTemplates: Map<FormTemplateId, FormTemplate>
    private val effectTemplates: Map<EffectTemplateId, EffectTemplate>

    companion object {

        const val CONFIG_FOLDER = "config/"
        private const val SKILL_TEMPLATES = "SkillTemplates.json"
        private const val FORMS_TEMPLATES = "FormTemplates.json"
        private const val EFFECT_TEMPLATES = "EffectTemplates.json"
    }

    init {

        skillTemplates = loadSkillTemplates().associateBy { it.id }
        formTemplates = loadFormTemplates().associateBy { it.id }
        effectTemplates = loadEffectTemplates().associateBy { it.id }
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findBy(id: SkillTemplateId): SkillTemplate? =

        skillTemplates[id]

    fun findBy(id: FormTemplateId): FormTemplate? =

        formTemplates[id]

    fun findBy(id: EffectTemplateId): EffectTemplate? =

        effectTemplates[id]

    fun findAllSkillTemplates(): Collection<SkillTemplate> =

        skillTemplates.values

    fun findAllFormSkillTemplates(): Collection<FormTemplate> =

        formTemplates.values

    fun findAllEffectSkillTemplates(): Collection<EffectTemplate> =

        effectTemplates.values

    fun exists(id: SkillTemplateId): Boolean =

        skillTemplates.containsKey(id)

    fun exists(id: FormTemplateId): Boolean =

        formTemplates.containsKey(id)

    fun exists(id: EffectTemplateId): Boolean =

        effectTemplates.containsKey(id)

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun loadSkillTemplates(): Collection<SkillTemplate> {

        val json = Gdx.files.internal("$CONFIG_FOLDER$SKILL_TEMPLATES").readString()
        val entities = deSerializer.deserialize(json, Array<SkillTemplateEntity>::class.java ).toList()
        return entities.map { skillTemplateAdapter.toDomain(it) }
    }

    private fun loadFormTemplates(): Collection<FormTemplate> {

        val json = Gdx.files.internal("$CONFIG_FOLDER$FORMS_TEMPLATES").readString()
        val entities = deSerializer.deserialize(json, Array<FormTemplateEntity>::class.java ).toList()
        return entities.map { skillTemplateAdapter.toDomain(it) }
    }

    private fun loadEffectTemplates(): Collection<EffectTemplate> {

        val json = Gdx.files.internal("$CONFIG_FOLDER$EFFECT_TEMPLATES").readString()
        val entities = deSerializer.deserialize(json, Array<EffectTemplateEntity>::class.java ).toList()
        return entities.map { skillTemplateAdapter.toDomain(it) }
    }
}
