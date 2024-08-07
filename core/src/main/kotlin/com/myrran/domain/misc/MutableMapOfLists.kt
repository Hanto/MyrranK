package com.myrran.domain.misc

class MutableMapOfLists<KEY, VALUE>(

    rootMapCreator: () -> MutableMap<KEY, MutableList<VALUE>>,
    private val innerListCreator: () -> MutableList<VALUE>,
    private val rootMap: MutableMap<KEY, MutableList<VALUE>> = rootMapCreator.invoke()

): MutableMap<KEY, MutableList<VALUE>> by rootMap
{
    operator fun set(key: KEY, value: VALUE) {

        val innerList = rootMap.computeIfAbsent(key) { innerListCreator.invoke() }
        innerList.add(value)
    }
}
