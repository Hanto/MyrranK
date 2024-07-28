package com.myrran.infraestructure

data class SkillBookEntity(

    val skillTemplates: List<SkillTemplateEntity>,
    val subSkillTemplates: List<SubSkillTemplateEntity>,
    val buffSkillTemplates: List<BuffSKillTemplateEntity>,
    val learnedSkills: List<LearnedEntity>,
    val learnedSubSKills: List<LearnedEntity>,
    val learnedBuffSkills: List<LearnedEntity>,
    val createdSkills: List<SkillEntity>
)

data class LearnedEntity(
    val id: String,
    val quantity: Int
)
