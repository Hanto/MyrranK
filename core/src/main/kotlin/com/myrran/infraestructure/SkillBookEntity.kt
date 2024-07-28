package com.myrran.infraestructure

data class PlayerSkillBookEntity(
    val learnedSkills: List<LearnedEntity>,
    val learnedSubSKills: List<LearnedEntity>,
    val learnedBuffSkills: List<LearnedEntity>,
    val createdSkills: List<SkillEntity>
)

data class WorldSkillBookEntity(
    val skillTemplates: List<SkillTemplateEntity>,
    val subSkillTemplates: List<SubSkillTemplateEntity>,
    val buffSkillTemplates: List<BuffSKillTemplateEntity>,
)

data class LearnedEntity(
    val id: String,
    val avaiable: Int,
    val total: Int
)
