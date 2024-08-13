package com.myrran.domain.mobs.common

import java.util.UUID

data class MobId(
    val value: UUID = UUID.randomUUID()
)
