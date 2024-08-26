package com.myrran.domain.events

import com.myrran.domain.entities.common.EntityId

sealed interface EffectEvent: Event

data class EffectAddedEvent(

    val effectableId: EntityId,
    val effectId: EntityId

): EffectEvent

data class EffectRemovedEvent(

    val effectableId: EntityId,
    val effectId: EntityId

): EffectEvent


data class EffectTickedEvent(

    val effectableId: EntityId,
    val effectId: EntityId

): EffectEvent
