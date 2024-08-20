package com.myrran.domain.entities.common.corporeal

sealed interface FixtureUserData {

    data class FixtureInfo(
        val id: FixtureId
    )
}

data class FixtureId(

    val value: String
)
