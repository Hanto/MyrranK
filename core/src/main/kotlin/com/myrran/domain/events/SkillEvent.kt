package com.myrran.domain.events

import com.myrran.domain.skills.created.BuffSkill
import com.myrran.domain.skills.created.SubBuffSkill
import com.myrran.domain.skills.created.SubSkill
import com.myrran.domain.skills.created.buff.BuffSkillSlotId
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.domain.skills.created.subskill.SubSkillSlotId

sealed interface SkillEvent: Event
{
    val skillId: SkillId
}

data class SkillCreatedEvent(override val skillId: SkillId): SkillEvent
data class SkillStatUpgradedEvent(override val skillId: SkillId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent
data class SkillRemovedEvent(override val skillId: SkillId): SkillEvent

data class SubSkillStatUpgradedEvent(override val skillId: SkillId, val subSlot: SubSkillSlotId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent
data class SubSkillChangedEvent(override val skillId: SkillId, val subSlot: SubSkillSlotId, val changedTo: SubSkill): SkillEvent
data class SubSkillRemovedEvent(override val skillId: SkillId, val removed: Collection<SubBuffSkill>): SkillEvent

data class BuffSkillStatUpgradedEvent(override val skillId: SkillId, val subSlot: SubSkillSlotId, val buffSlot: BuffSkillSlotId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent
data class BuffSkillChangedEvent(override val skillId: SkillId, val subSlot: SubSkillSlotId, val buffSlot: BuffSkillSlotId, val changedTo: BuffSkill): SkillEvent
data class BuffSkillRemovedEvent(override val skillId: SkillId, val removed: BuffSkill): SkillEvent
