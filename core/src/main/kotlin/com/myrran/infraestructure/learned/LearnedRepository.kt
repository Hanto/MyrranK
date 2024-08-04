package com.myrran.infraestructure.learned

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.utils.DeSerializer
import com.myrran.domain.utils.Quantity
import com.myrran.domain.utils.QuantityMap

class LearnedRepository(

    private val deSerializer: DeSerializer
)
{
    companion object {

        private const val LEARNED_SKILL_TEMPLATES_JSON = "LearnedSkillTemplates.json"
        private const val LEARNED_SUBSKILL_TEMPLATES_JSON = "LearnedSubSkillTemplates.json"
        private const val LEARNED_BUFFSKILL_TEMPLATES_JSON = "LearnedBuffSkillTemplates.json"
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findLearnedSkillTemplates(): QuantityMap<SkillTemplateId> =

        runCatching {

            val json = Gdx.files.local(LEARNED_SKILL_TEMPLATES_JSON).readString()
            val entities = deSerializer.deserialize(json, Array<LearnedEntity>::class.java).toList()
            entities.associate { SkillTemplateId(it.id) to Quantity(it.avaiable, it.total) }
                .toMutableMap()
                .let { QuantityMap(it) }

        }.getOrElse { QuantityMap() }

    fun findLearnedSubSkillTemplates(): QuantityMap<SubSkillTemplateId> =

        runCatching {

            val json = Gdx.files.local(LEARNED_SUBSKILL_TEMPLATES_JSON).readString()
            val entities = deSerializer.deserialize(json, Array<LearnedEntity>::class.java).toList()
            entities.associate { SubSkillTemplateId(it.id) to Quantity(it.avaiable, it.total) }
                .toMutableMap()
                .let { QuantityMap(it) }

        }.getOrElse { QuantityMap() }

    fun findLearnedBuffSkillTemplates(): QuantityMap<BuffSkillTemplateId> =

        runCatching {

            val json = Gdx.files.local(LEARNED_BUFFSKILL_TEMPLATES_JSON).readString()
            val entities = deSerializer.deserialize(json, Array<LearnedEntity>::class.java).toList()
            entities.associate { BuffSkillTemplateId(it.id) to Quantity(it.avaiable, it.total) }
                .toMutableMap()
                .let { QuantityMap(it) }

        }.getOrElse { QuantityMap() }

    fun saveLearnedSkillTemplates(learnedSkillTemplates: QuantityMap<SkillTemplateId>) {

        val entities = learnedSkillTemplates.entries.map { LearnedEntity(it.key.value, it.value.available, it.value.total) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local(LEARNED_SKILL_TEMPLATES_JSON).writeString(json, false)
    }

    fun saveLearnedSubSkillTemplates(learnedSkillTemplates: QuantityMap<SubSkillTemplateId>) {

        val entities = learnedSkillTemplates.entries.map { LearnedEntity(it.key.value, it.value.available, it.value.total) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local(LEARNED_SUBSKILL_TEMPLATES_JSON).writeString(json, false)
    }

    fun saveLearnedBuffSkillTemplates(learnedSkillTemplates: QuantityMap<BuffSkillTemplateId>) {

        val entities = learnedSkillTemplates.entries.map { LearnedEntity(it.key.value, it.value.available, it.value.total) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local(LEARNED_BUFFSKILL_TEMPLATES_JSON).writeString(json, false)
    }
}
