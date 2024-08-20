package com.myrran.domain.mobs.common.corporeal

sealed interface FixtureUserData {

    data class FixtureInfo(
        val id: FixtureId
    )
}

data class FixtureId(

    val value: String
)
