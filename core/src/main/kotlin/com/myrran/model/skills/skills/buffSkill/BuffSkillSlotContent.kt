package com.myrran.model.skills.skills.buffSkill

sealed interface BuffSkillSlotContent {

    data object NoBuffSkill: BuffSkillSlotContent
}
