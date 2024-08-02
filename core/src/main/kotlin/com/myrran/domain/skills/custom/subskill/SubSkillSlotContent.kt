package com.myrran.domain.skills.custom.subskill

sealed interface SubSkillSlotContent {

    data object NoSubSkill: SubSkillSlotContent
}
