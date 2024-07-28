package com.myrran.infraestructure

data class SkillBookEntity(

    val skillTemplates: List<SkillTemplateEntity>,
    val subSkillTemplates: List<SubSkillTemplateEntity>,
    val buffSkillTemplates: List<BuffSKillTemplateEntity>,
    val createdSkills: List<SkillEntity>
)
