package com.myrran.infraestructure.learnedskilltemplate

import com.badlogic.gdx.Gdx
import com.myrran.domain.Quantity
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.utils.DeSerializer

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

    fun saveSkill(quantity: Quantity<SkillTemplateId>) {

        skillTemplates[quantity.value] = quantity
        saveSkillTemplates()
    }

    fun saveSkills(quantityList: Collection<Quantity<SkillTemplateId>>) {

        quantityList.forEach { skillTemplates[it.value] = it }
        saveSkillTemplates()
    }

    fun saveSub(quantity: Quantity<SubSkillTemplateId>) {

        subSkillsTemplates[quantity.value] = quantity
        saveSubSkillTemplates()
    }

    fun saveSubs(quantityList: Collection<Quantity<SubSkillTemplateId>>) {

        quantityList.forEach { subSkillsTemplates[it.value] = it }
        saveSubSkillTemplates()
    }

    fun saveBuff(quantity: Quantity<BuffSkillTemplateId>) {

        buffSkillsTemplates[quantity.value] = quantity
        saveBuffSkillTemplates()
    }

    fun saveBuffs(quantityList: Collection<Quantity<BuffSkillTemplateId>>) {

        quantityList.forEach { buffSkillsTemplates[it.value] = it }
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
