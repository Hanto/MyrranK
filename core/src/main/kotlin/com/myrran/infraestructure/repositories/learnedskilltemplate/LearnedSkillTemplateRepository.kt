package com.myrran.infraestructure.repositories.learnedskilltemplate

import com.badlogic.gdx.Gdx
import com.myrran.domain.misc.DeSerializer
import com.myrran.domain.misc.Quantity
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId

class LearnedSkillTemplateRepository(

    private val deSerializer: DeSerializer
)
{
    private val skillTemplates: MutableMap<SkillTemplateId, Quantity<SkillTemplateId>>
    private val subSkillsTemplates: MutableMap<SubSkillTemplateId, Quantity<SubSkillTemplateId>>
    private val buffSkillsTemplates: MutableMap<BuffSkillTemplateId, Quantity<BuffSkillTemplateId>>

    companion object {

        private const val LEARNED_SKILL_TEMPLATES_JSON = "LearnedSkillTemplates.json"
        private const val LEARNED_SUBSKILL_TEMPLATES_JSON = "LearnedSubSkillTemplates.json"
        private const val LEARNED_BUFFSKILL_TEMPLATES_JSON = "LearnedBuffSkillTemplates.json"
    }

    init {

        skillTemplates = loadSkillTemplates()
        subSkillsTemplates = loadSubSkillTemplates()
        buffSkillsTemplates = loadBuffSkillTemplates()
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findBy(id: SkillTemplateId): Quantity<SkillTemplateId> = skillTemplates[id] ?: Quantity.zero(id)
    fun findBy(id: SubSkillTemplateId): Quantity<SubSkillTemplateId> = subSkillsTemplates[id] ?: Quantity.zero(id)
    fun findBy(id: BuffSkillTemplateId): Quantity<BuffSkillTemplateId> = buffSkillsTemplates[id] ?: Quantity.zero(id)
    fun findAllSkillTemplates() = skillTemplates.values
    fun findAllSubSkillTemplates() = subSkillsTemplates.values
    fun findAllBuffSkillTemplates() = buffSkillsTemplates.values

    fun saveSkill(id: Quantity<SkillTemplateId>) {

        skillTemplates[id.value] = id
        saveSkillTemplates()
    }

    fun saveSkills(list: Collection<Quantity<SkillTemplateId>>) {

        list.forEach { skillTemplates[it.value] = it }
        saveSkillTemplates()
    }

    fun saveSub(id: Quantity<SubSkillTemplateId>) {

        subSkillsTemplates[id.value] = id
        saveSubSkillTemplates()
    }

    fun saveSubs(list: Collection<Quantity<SubSkillTemplateId>>) {

        list.forEach { subSkillsTemplates[it.value] = it }
        saveSubSkillTemplates()
    }

    fun saveBuff(id: Quantity<BuffSkillTemplateId>) {

        buffSkillsTemplates[id.value] = id
        saveBuffSkillTemplates()
    }

    fun saveBuffs(list: Collection<Quantity<BuffSkillTemplateId>>) {

        list.forEach { buffSkillsTemplates[it.value] = it }
        saveBuffSkillTemplates()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun saveSkillTemplates() {

        val entities = skillTemplates.entries.map { LearnedSkillTemplateEntity(it.key.value, it.value.available, it.value.total) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local(LEARNED_SKILL_TEMPLATES_JSON).writeString(json, false)
    }

    private fun saveSubSkillTemplates() {

        val entities = subSkillsTemplates.entries.map { LearnedSkillTemplateEntity(it.key.value, it.value.available, it.value.total) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local(LEARNED_SUBSKILL_TEMPLATES_JSON).writeString(json, false)
    }

    private fun saveBuffSkillTemplates() {

        val entities = buffSkillsTemplates.entries.map { LearnedSkillTemplateEntity(it.key.value, it.value.available, it.value.total) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local(LEARNED_BUFFSKILL_TEMPLATES_JSON).writeString(json, false)
    }

    private fun loadSkillTemplates(): MutableMap<SkillTemplateId, Quantity<SkillTemplateId>> =

        runCatching {

            val json = Gdx.files.local(LEARNED_SKILL_TEMPLATES_JSON).readString()
            val entities = deSerializer.deserialize(json, Array<LearnedSkillTemplateEntity>::class.java).toList()
            entities.associate { SkillTemplateId(it.id) to Quantity(SkillTemplateId(it.id), it.avaiable, it.total) }.toMutableMap()

        }.getOrElse { mutableMapOf() }

    private fun loadSubSkillTemplates(): MutableMap<SubSkillTemplateId, Quantity<SubSkillTemplateId>> =

        runCatching {

            val json = Gdx.files.local(LEARNED_SUBSKILL_TEMPLATES_JSON).readString()
            val entities = deSerializer.deserialize(json, Array<LearnedSkillTemplateEntity>::class.java).toList()
            return entities.associate { SubSkillTemplateId(it.id) to Quantity(SubSkillTemplateId(it.id), it.avaiable, it.total) }.toMutableMap()

        }.getOrElse { mutableMapOf() }

    private fun loadBuffSkillTemplates(): MutableMap<BuffSkillTemplateId, Quantity<BuffSkillTemplateId>> =

        runCatching {

            val json = Gdx.files.local(LEARNED_BUFFSKILL_TEMPLATES_JSON).readString()
            val entities = deSerializer.deserialize(json, Array<LearnedSkillTemplateEntity>::class.java).toList()
            return entities.associate { BuffSkillTemplateId(it.id) to Quantity(BuffSkillTemplateId(it.id) ,it.avaiable, it.total) }.toMutableMap()

        }.getOrElse { mutableMapOf() }
}
