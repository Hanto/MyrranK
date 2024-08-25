package com.myrran.domain.entities.mob.spells.effect.stackable

import com.myrran.domain.skills.created.stat.Stat

class StackableComponent(

    private var currentStack: Int,
    private var maxStacks: Int

): Stackable
{
    override fun numberOfStacks(): Int =

        currentStack

    override fun increaseStack() {

        currentStack = (currentStack + 1).coerceAtMost(maxStacks)
    }
}
