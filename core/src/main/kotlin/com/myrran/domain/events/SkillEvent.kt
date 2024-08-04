package com.myrran.domain.events

import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId

sealed interface SkillEvent: Event

data class SubSkillStatUpgradedEvent(val skillId: SkillId, val subId: SubSkillSlotId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent
data class BuffSkillStatUpgradedEvent(val skillId: SkillId, val subId: SubSkillSlotId, val buffId: BuffSkillSlotId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent
data class SkillStatUpgradedEvent(val skillId: SkillId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent
data class SubSkillChangedEvent(val subId: SubSkillSlotId): SkillEvent
data class BuffSkillChangedEvent(val subId: SubSkillSlotId, val buffId: BuffSkillSlotId): SkillEvent
