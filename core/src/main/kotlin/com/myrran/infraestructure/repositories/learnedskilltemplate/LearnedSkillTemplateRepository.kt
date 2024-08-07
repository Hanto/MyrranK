package com.myrran.infraestructure.repositories.learnedskilltemplate

import com.badlogic.gdx.Gdx
import com.myrran.domain.misc.DeSerializer
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.effect.EffectTemplateId
import com.myrran.domain.skills.templates.form.FormTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId

class LearnedSkillTemplateRepository(

    private val deSerializer: DeSerializer
)
{
    private val skillTemplates: MutableMap<SkillTemplateId, Quantity<SkillTemplateId>>
    private val formSkillsTemplates: MutableMap<FormTemplateId, Quantity<FormTemplateId>>
    private val effectSkillsTemplates: MutableMap<EffectTemplateId, Quantity<EffectTemplateId>>

    companion object {

        private const val LEARNED_SKILL_TEMPLATES_JSON = "LearnedSkillTemplates.json"
        private const val LEARNED_FORM_TEMPLATES_JSON = "LearnedFormTemplates.json"
        private const val LEARNED_EFFECT_TEMPLATES_JSON = "LearnedEffectTemplates.json"
    }

    init {

        skillTemplates = loadSkillTemplates()
        formSkillsTemplates = loadFormSkillTemplates()
        effectSkillsTemplates = loadEffectSkillTemplates()
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findBy(id: SkillTemplateId): Quantity<SkillTemplateId> = skillTemplates[id] ?: Quantity.zero(id)
    fun findBy(id: FormTemplateId): Quantity<FormTemplateId> = formSkillsTemplates[id] ?: Quantity.zero(id)
    fun findBy(id: EffectTemplateId): Quantity<EffectTemplateId> = effectSkillsTemplates[id] ?: Quantity.zero(id)
    fun findAllSkillTemplates() = skillTemplates.values
    fun findAllFormTemplates() = formSkillsTemplates.values
    fun findAllEffectTemplates() = effectSkillsTemplates.values

    fun saveSkill(id: Quantity<SkillTemplateId>) {

        skillTemplates[id.value] = id
        saveSkillTemplates()
    }

    fun saveSkills(list: Collection<Quantity<SkillTemplateId>>) {

        list.forEach { skillTemplates[it.value] = it }
        saveSkillTemplates()
    }

    fun saveForm(id: Quantity<FormTemplateId>) {

        formSkillsTemplates[id.value] = id
        saveFormTemplates()
    }

    fun saveForms(list: Collection<Quantity<FormTemplateId>>) {

        list.forEach { formSkillsTemplates[it.value] = it }
        saveFormTemplates()
    }

    fun saveEffect(id: Quantity<EffectTemplateId>) {

        effectSkillsTemplates[id.value] = id
        saveEffectTemplates()
    }

    fun saveEffects(list: Collection<Quantity<EffectTemplateId>>) {

        list.forEach { effectSkillsTemplates[it.value] = it }
        saveEffectTemplates()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun saveSkillTemplates() {

        val entities = skillTemplates.entries.map { LearnedSkillTemplateEntity(it.key.value, it.value.available, it.value.total) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local(LEARNED_SKILL_TEMPLATES_JSON).writeString(json, false)
    }

    private fun saveFormTemplates() {

        val entities = formSkillsTemplates.entries.map { LearnedSkillTemplateEntity(it.key.value, it.value.available, it.value.total) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local(LEARNED_FORM_TEMPLATES_JSON).writeString(json, false)
    }

    private fun saveEffectTemplates() {

        val entities = effectSkillsTemplates.entries.map { LearnedSkillTemplateEntity(it.key.value, it.value.available, it.value.total) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local(LEARNED_EFFECT_TEMPLATES_JSON).writeString(json, false)
    }

    private fun loadSkillTemplates(): MutableMap<SkillTemplateId, Quantity<SkillTemplateId>> =

        runCatching {

            val json = Gdx.files.local(LEARNED_SKILL_TEMPLATES_JSON).readString()
            val entities = deSerializer.deserialize(json, Array<LearnedSkillTemplateEntity>::class.java).toList()
            entities.associate { SkillTemplateId(it.id) to Quantity(SkillTemplateId(it.id), it.avaiable, it.total) }.toMutableMap()

        }.getOrElse { mutableMapOf() }

    private fun loadFormSkillTemplates(): MutableMap<FormTemplateId, Quantity<FormTemplateId>> =

        runCatching {

            val json = Gdx.files.local(LEARNED_FORM_TEMPLATES_JSON).readString()
            val entities = deSerializer.deserialize(json, Array<LearnedSkillTemplateEntity>::class.java).toList()
            return entities.associate { FormTemplateId(it.id) to Quantity(FormTemplateId(it.id), it.avaiable, it.total) }.toMutableMap()

        }.getOrElse { mutableMapOf() }

    private fun loadEffectSkillTemplates(): MutableMap<EffectTemplateId, Quantity<EffectTemplateId>> =

        runCatching {

            val json = Gdx.files.local(LEARNED_EFFECT_TEMPLATES_JSON).readString()
            val entities = deSerializer.deserialize(json, Array<LearnedSkillTemplateEntity>::class.java).toList()
            return entities.associate { EffectTemplateId(it.id) to Quantity(EffectTemplateId(it.id) ,it.avaiable, it.total) }.toMutableMap()

        }.getOrElse { mutableMapOf() }
}
