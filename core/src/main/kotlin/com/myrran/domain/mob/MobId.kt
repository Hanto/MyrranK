package com.myrran.domain.mob

import java.util.UUID

data class MobId(
    val value: UUID = UUID.randomUUID()
)
