package com.myrran.domain.entities.mob.spells.effect.stackable

interface Stackable {

    fun numberOfStacks(): Int
    fun increaseStack()
}
