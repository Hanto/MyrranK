package com.myrran.domain.skills.skills.buffskill

sealed interface BuffSkillSlotContent {

    data object NoBuffSkill: BuffSkillSlotContent
}
