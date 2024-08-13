package com.myrran.domain.mobs

import java.util.UUID

data class MobId(
    val value: UUID = UUID.randomUUID()
)
