package com.myrran.infraestructure.skill

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.created.Skill
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.utils.DeSerializer

class CreatedSkillRepository(

    private val skillAdapter: SkillAdapter,
    private val deSerializer: DeSerializer
)
{
    companion object {

        const val SKILLS_JSON = "Skill.json"
    }

    private var createdSkills: MutableMap<SkillId, Skill>

    init {

        createdSkills = loadSkills().associateBy { it.id }.toMutableMap()
    }

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    fun findBy(skillId: SkillId): Skill? =

        createdSkills[skillId]

    fun findAll(): Collection<Skill> =

        createdSkills.values

    fun removeBy(id: SkillId) {

        createdSkills.remove(id)
        saveSkills()
    }

    fun save(skill: Skill) {

        createdSkills[skill.id] = skill
        saveSkills()
    }

    fun save(skills: Collection<Skill>) {

        createdSkills = skills.associateBy { it.id }.toMutableMap()
        saveSkills()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun loadSkills(): Collection<Skill> =

        runCatching {

            val json = Gdx.files.local(SKILLS_JSON).readString()
            val entities = deSerializer.deserialize(json, Array<SkillEntity>::class.java).toList()
            entities.map { skillAdapter.toDomain(it) }

        }.getOrElse { emptyList() }

    private fun saveSkills() {

        val domains = createdSkills.values
        val entities = domains.map { skillAdapter.fromDomain(it) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local(SKILLS_JSON).writeString(json, false)
    }
}
