package com.myrran.domain.utils

import com.myrran.domain.utils.mapttl.MapTTL
import java.util.concurrent.TimeUnit

class MapOfMapTTL<KEY1, KEY2, VALUE>(

    private val defaultTTL: ElapsedTime = ElapsedTime.of(1, TimeUnit.MINUTES),
    rootMapCreator: () -> MutableMap<KEY1, MapTTL<KEY2, VALUE>> =  { mutableMapOf() },
    private val rootMap: MutableMap<KEY1, MapTTL<KEY2, VALUE>> = rootMapCreator.invoke()

): MutableMap<KEY1, MapTTL<KEY2, VALUE>> by rootMap
{
    operator fun set(key1: KEY1, key2: KEY2, value: VALUE) {

        val innerMap = rootMap.computeIfAbsent(key1) { MapTTL(defaultTTL = defaultTTL) }
        innerMap[key2] = value
    }

    operator fun set(key1: KEY1, key2: KEY2, timeToLive: ElapsedTime, value: VALUE) {

        val innerMap = rootMap.computeIfAbsent(key1) { MapTTL(timeToLive) }
        innerMap[key2] = value
    }

    operator fun get(key1: KEY1, key2: KEY2): VALUE? =

        rootMap[key1]?.get(key2)
}
