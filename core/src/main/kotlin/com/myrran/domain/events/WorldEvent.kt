package com.myrran.domain.events

import com.badlogic.gdx.math.Vector2
import com.myrran.domain.entities.common.Entity
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.Mob
import com.myrran.domain.entities.common.caster.Caster
import com.myrran.domain.entities.mob.enemy.Enemy
import com.myrran.domain.entities.mob.spells.form.Form
import com.myrran.domain.entities.mob.spells.spell.Spell
import com.myrran.domain.misc.metrics.PositionMeters
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.skill.Skill

sealed interface WorldEvent: Event

data class PlayerSpellCastedEvent(

    val skill: Skill,
    val caster: Entity,
    val origin: PositionMeters,
    val target: PositionMeters

): WorldEvent

data class FormSpellCastedEvent(

    val formSkill: FormSkill,
    val caster: Entity,
    val origin: PositionMeters,
    val direction: Vector2,

    ): WorldEvent

data class SpellCreatedEvent(

    val spell: Spell

): WorldEvent

data class FormCreatedEvent(

    val form: Form

): WorldEvent

data class MobCreatedEvent(

    val mob: Enemy

): WorldEvent

data class MobRemovedEvent(

    val mob: Mob

): WorldEvent
