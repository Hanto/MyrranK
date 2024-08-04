package com.myrran.infraestructure.skill

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.custom.skill.Skill
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

    fun findSkillById(skillId: SkillId): Skill? =

        createdSkills[skillId]

    fun findSkills(): Collection<Skill> =

        createdSkills.values

    fun saveSkill(skill: Skill) {

        createdSkills[skill.id] = skill

        val skills = loadSkills() + skill
        val json = deSerializer.serialize(skills)
        Gdx.files.local("Skill.json").writeString(json, false)
    }

    fun saveSkills(skills: Collection<Skill>) {

        createdSkills = skills.associateBy { it.id }.toMutableMap()

        val json = deSerializer.serialize(createdSkills)
        Gdx.files.local("Skill.json").writeString(json, false)
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun loadSkills(): Collection<Skill> =

        runCatching {

            val json = Gdx.files.local("Skill.json").readString()
            val entities = deSerializer.deserialize(json, Array<SkillEntity>::class.java).toList()
            entities.map { skillAdapter.toDomain(it) }

        }.getOrElse { emptyList() }
}
