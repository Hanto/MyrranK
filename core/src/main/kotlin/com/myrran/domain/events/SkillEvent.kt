package com.myrran.domain.events

import com.myrran.domain.skills.custom.BuffSkill
import com.myrran.domain.skills.custom.SubBuffSkill
import com.myrran.domain.skills.custom.SubSkill
import com.myrran.domain.skills.custom.buff.BuffSkillSlotId
import com.myrran.domain.skills.custom.skill.SkillId
import com.myrran.domain.skills.custom.stat.NumUpgrades
import com.myrran.domain.skills.custom.stat.StatId
import com.myrran.domain.skills.custom.subskill.SubSkillSlotId

sealed interface SkillEvent: Event

data class SkillStatUpgradedEvent(val skillId: SkillId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent

data class SubSkillStatUpgradedEvent(val skillId: SkillId, val subSlot: SubSkillSlotId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent
data class SubSkillChangedEvent(val skillId: SkillId, val subSlot: SubSkillSlotId, val changedTo: SubSkill): SkillEvent
data class SubSkillRemovedEvent(val skillId: SkillId, val removed: List<SubBuffSkill>): SkillEvent

data class BuffSkillStatUpgradedEvent(val skillId: SkillId, val subSlot: SubSkillSlotId, val buffSlot: BuffSkillSlotId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent
data class BuffSkillChangedEvent(val skillId: SkillId, val subSlot: SubSkillSlotId, val buffSlot: BuffSkillSlotId, val changedTo: BuffSkill): SkillEvent
data class BuffSkillRemovedEvent(val skillId: SkillId, val removed: BuffSkill): SkillEvent

