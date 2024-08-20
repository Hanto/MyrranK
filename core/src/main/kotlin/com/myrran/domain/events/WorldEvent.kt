package com.myrran.domain.events

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.entities.common.Mob
import com.myrran.domain.entities.common.caster.Caster
import com.myrran.domain.entities.mob.enemy.Enemy
import com.myrran.domain.entities.mob.spells.spell.Spell
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.skills.created.form.FormSkill

sealed interface WorldEvent: Event

data class PlayerSpellCastedEvent(

    val caster: Caster,
    val origin: PositionMeters,

): WorldEvent

data class FormSpellCastedEvent(

    val formSkill: FormSkill,
    val origin: PositionMeters,
    val direction: Vector2,

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
