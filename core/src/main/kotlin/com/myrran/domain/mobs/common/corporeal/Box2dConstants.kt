package com.myrran.domain.mobs.common.corporeal

class Box2dFilters {

    companion object {

        const val WALLS: Short =        0b000000001
        const val PLAYER: Short =       0b000000010
        const val SPELL: Short =        0b000000100
        const val ENEMY: Short =        0b000001000
        const val ENEMY_SENSOR: Short = 0b000010000
        const val LIGHT_PLAYER: Short = 0b000100000
        const val LIGHT_SPELLS: Short = 0b001000000
        const val ENEMY_LOS: Short =    0b010000000
    }
}

class Box2dFixtureIds {

    companion object {

        val BODY = FixtureId("BODY")
        val PROXIMITY = FixtureId("BODY")
        val CONE_OF_SIGHT = FixtureId("BODY")
    }
}
