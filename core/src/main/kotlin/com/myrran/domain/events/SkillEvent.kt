package com.myrran.domain.events

import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.form.FormSkillSlotId
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.skills.created.stat.StatId

sealed interface SkillEvent: Event
{
    val skillId: SkillId
}

// SKILL:
//------------------------------------------------------------------------------------------------------------

data class SkillCreatedEvent(
    override val skillId: SkillId
): SkillEvent

data class SkillStatUpgradedEvent(
    override val skillId: SkillId,
    val statId: StatId
): SkillEvent

data class SkillRemovedEvent(
    override val skillId: SkillId
): SkillEvent

// FORM:
//------------------------------------------------------------------------------------------------------------

data class FormSkillStatUpgradedEvent(
    override val skillId: SkillId,
    val formSlot: FormSkillSlotId,
    val statId: StatId
): SkillEvent

data class FormSkillChangedEvent(
    override val skillId: SkillId,
    val formSlot: FormSkillSlotId
): SkillEvent

data class FormSkillRemovedEvent(
    override val skillId: SkillId,
    val formSlot: FormSkillSlotId
): SkillEvent

// EFFECT:
//------------------------------------------------------------------------------------------------------------

data class EffectSkillStatUpgradedEvent(
    override val skillId: SkillId,
    val formSlot: FormSkillSlotId,
    val effectSlot: EffectSkillSlotId,
    val statId: StatId
): SkillEvent

data class EffectSkillChangedEvent(
    override val skillId: SkillId,
    val formSlot: FormSkillSlotId,
    val effectSlot: EffectSkillSlotId
): SkillEvent

data class EffectSkillRemovedEvent(
    override val skillId: SkillId,
    val formSlot: FormSkillSlotId,
    val effectSlot: EffectSkillSlotId
): SkillEvent
