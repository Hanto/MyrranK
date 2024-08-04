package com.myrran.infraestructure.skill

import com.badlogic.gdx.Gdx
import com.myrran.domain.skills.custom.skill.Skill
import com.myrran.domain.utils.DeSerializer

class SkillRepository(

    private val skillAdapter: SkillAdapter,
    private val deSerializer: DeSerializer
)
{
    // MAIN
    //--------------------------------------------------------------------------------------------------------

    fun findSkills(): Collection<Skill> =

        runCatching {

            val json = Gdx.files.local("Skill.json").readString()
            val entities = deSerializer.deserialize(json, Array<SkillEntity>::class.java).toList()
            entities.map { skillAdapter.toDomain(it) }

        }.getOrElse { emptyList() }

    fun saveSkill(skill: Skill) {

        val skills = findSkills() + skill
        val json = deSerializer.serialize(skills)
        Gdx.files.local("Skill.json").writeString(json, false)
    }

    fun saveSkills(skills: Collection<Skill>) {

        val json = deSerializer.serialize(skills)
        Gdx.files.local("Skill.json").writeString(json, false)
    }
}
