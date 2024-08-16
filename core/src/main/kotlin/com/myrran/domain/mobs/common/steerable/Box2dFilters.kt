package com.myrran.domain.mobs.common.steerable

class Box2dFilters {

    companion object {

        const val WALLS: Short =        0b00000001
        const val PLAYER: Short =       0b00000010
        const val BULLET: Short =       0b00000100
        const val ENEMY: Short =        0b00001000
        const val ENEMY_SENSOR: Short = 0b00010000
        const val LIGHT_PLAYER: Short = 0b00100000
        const val LIGHT_SPELLS: Short = 0b01000000
    }
}
