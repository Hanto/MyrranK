package com.myrran.domain.events

import com.myrran.domain.mobs.Mob
import com.myrran.domain.mobs.MobId
import com.myrran.domain.mobs.spells.spell.Spell
import com.myrran.domain.mobs.steerable.metrics.Meter
import com.myrran.domain.mobs.steerable.metrics.Position
import com.myrran.domain.skills.created.skill.SkillId

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
