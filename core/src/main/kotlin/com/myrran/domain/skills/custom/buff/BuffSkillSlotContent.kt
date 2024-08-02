package com.myrran.domain.skills.custom.buff

sealed interface BuffSkillSlotContent {

    data object NoBuffSkill: BuffSkillSlotContent
}
