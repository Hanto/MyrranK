package com.myrran.domain.events

import com.myrran.domain.mob.Mob
import com.myrran.domain.mob.MobId
import com.myrran.domain.mob.metrics.Meter
import com.myrran.domain.mob.metrics.Position
import com.myrran.domain.skills.created.skill.SkillId
import com.myrran.domain.spells.spell.Spell

sealed interface WorldEvent: Event

data class PlayerSpellCastedEvent(

    val mobId: MobId,
    val skillId: SkillId,
    val origin: Position<Meter>,
    val target: Position<Meter>

): WorldEvent

data class SpellCreatedEvent(

    val spell: Spell

): WorldEvent

data class MobRemovedEvent(

    val mob: Mob

): WorldEvent
