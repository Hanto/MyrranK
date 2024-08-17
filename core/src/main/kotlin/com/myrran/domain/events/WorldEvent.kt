package com.myrran.domain.events

import com.myrran.domain.mobs.common.Mob
import com.myrran.domain.mobs.common.caster.Caster
import com.myrran.domain.mobs.common.metrics.PositionMeters
import com.myrran.domain.mobs.mob.Enemy
import com.myrran.domain.mobs.spells.spell.Spell

sealed interface WorldEvent: Event

data class PlayerSpellCastedEvent(

    val caster: Caster,
    val origin: PositionMeters,

): WorldEvent

data class SpellCreatedEvent(

    val spell: Spell

): WorldEvent

data class MobCreatedEvent(

    val mob: Enemy

): WorldEvent

data class MobRemovedEvent(

    val mob: Mob

): WorldEvent
