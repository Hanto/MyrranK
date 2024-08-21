package com.myrran.domain.events

import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.vulnerable.HP

sealed interface EntityEvent: Event {

    val entityId: EntityId
}

data class EntityHPsReducedEvent(

    override val entityId: EntityId,
    val reducedHP: HP,

): EntityEvent
