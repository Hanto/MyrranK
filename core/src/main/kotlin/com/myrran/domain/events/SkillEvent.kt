package com.myrran.domain.events

import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId

sealed interface SkillEvent: Event

data object StatUpgradedEvent: SkillEvent
data class SubSkillChangedEvent(val subId: SubSkillSlotId): SkillEvent
data class BuffSkillChangedEvent(val subId: SubSkillSlotId, val buffId: BuffSkillSlotId): SkillEvent
