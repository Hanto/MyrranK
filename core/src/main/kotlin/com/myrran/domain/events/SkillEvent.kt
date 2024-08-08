package com.myrran.domain.events

import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.skills.created.skill.SkillsRemoved
import com.myrran.domain.skills.created.stat.NumUpgrades
import com.myrran.domain.skills.created.stat.StatId

sealed interface SkillEvent: Event
{
    val skillId: SkillId
}

data class SkillCreatedEvent(override val skillId: SkillId): SkillEvent
data class SkillStatUpgradedEvent(override val skillId: SkillId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent
data class SkillRemovedEvent(override val skillId: SkillId, val removed: SkillsRemoved): SkillEvent

data class FormSkillStatUpgradedEvent(override val skillId: SkillId, val formSlot: FormSkillSlotId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent
data class FormSkillChangedEvent(override val skillId: SkillId, val formSlot: FormSkillSlotId, val changedTo: FormSkill): SkillEvent
data class FormSkillRemovedEvent(override val skillId: SkillId, val removed: SkillsRemoved): SkillEvent

data class EffectSkillStatUpgradedEvent(override val skillId: SkillId, val formSlot: FormSkillSlotId, val effectSlot: EffectSkillSlotId, val statId: StatId, val numUpgrades: NumUpgrades): SkillEvent
data class EffectSkillChangedEvent(override val skillId: SkillId, val formSlot: FormSkillSlotId, val effectSlot: EffectSkillSlotId, val changedTo: EffectSkill): SkillEvent
data class EffectSkillRemovedEvent(override val skillId: SkillId, val removed: EffectSkill): SkillEvent
