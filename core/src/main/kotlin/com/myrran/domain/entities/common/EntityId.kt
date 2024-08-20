package com.myrran.domain.entities.common

import java.util.UUID

data class EntityId(

    val value: UUID = UUID.randomUUID()
)
