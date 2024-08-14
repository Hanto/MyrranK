package com.myrran.domain.mobs.common.steerable

class Box2dFilters {

    companion object {

        const val WALLS: Short =  0b01000
        const val PLAYER: Short = 0b00010
        const val BULLET: Short = 0b00001
        const val BODY: Short =   0b00100
        const val LIGHT: Short =  0b10000
    }
}
