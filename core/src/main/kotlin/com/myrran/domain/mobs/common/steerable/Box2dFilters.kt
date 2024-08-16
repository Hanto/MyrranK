package com.myrran.domain.mobs.common.steerable

class Box2dFilters {

    companion object {

        const val WALLS: Short =        0b0000001
        const val PLAYER: Short =       0b0000010
        const val BULLET: Short =       0b0000100
        const val ENEMY: Short =        0b0001000
        const val LIGHT_PLAYER: Short = 0b0010000
        const val LIGHT_SPELLS: Short = 0b0100000
    }
}
