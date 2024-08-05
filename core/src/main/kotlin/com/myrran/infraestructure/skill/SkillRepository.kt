package com.myrran.infraestructure.skill

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.custom.Skill
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.utils.DeSerializer

class SkillRepository(

    private val skillAdapter: SkillAdapter,
    private val deSerializer: DeSerializer
)
{
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

    fun save(skill: Skill) {

        createdSkills[skill.id] = skill

        val domains = createdSkills.values
        val entities = domains.map { skillAdapter.fromDomain(it) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local("Skill.json").writeString(json, false)
    }

    fun save(skills: Collection<Skill>) {

        createdSkills = skills.associateBy { it.id }.toMutableMap()

        val domains = createdSkills.values
        val entities = domains.map { skillAdapter.fromDomain(it) }
        val json = deSerializer.serialize(entities)
        Gdx.files.local("Skill.json").writeString(json, false)
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun loadSkills(): Collection<Skill> =

        runCatching {

            val json = Gdx.files.local("Skill.json").readString()
            val entities = deSerializer.deserialize(json, Array<SkillEntity>::class.java).toList()
            entities.map { skillAdapter.toDomain(it) }

        }.getOrElse {

            println(it)
            emptyList() }
}
