package com.myrran.domain.utils.mapttl

import com.badlogic.gdx.utils.Disposable
import com.myrran.domain.utils.ElapsedTime
import java.util.concurrent.DelayQueue
import java.util.concurrent.TimeUnit

class MapTTL<KEY, VALUE>(

    private val defaultTTL: ElapsedTime = ElapsedTime.of(1, TimeUnit.MINUTES)

): Disposable {

    private val internalMap: MutableMap<KEY, VALUE> = mutableMapOf()
    private val expiringKeys: MutableMap<KEY, ExpiringKey<KEY>> = mutableMapOf()
    private val delayQueue = DelayQueue<ExpiringKey<KEY>>()

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    operator fun set(key: KEY, value: VALUE) =

        this.set(key, defaultTTL, value)

    operator fun set(key: KEY, timeToLive: ElapsedTime, value: VALUE) {

        expiringKeys.remove(key)?.expire()
        delayQueue.removeExpired()

        val expiringKey = ExpiringKey(key = key, ttl = timeToLive)
        internalMap[key]?.dispose()
        internalMap[key] = value
        expiringKeys[key] = expiringKey
        delayQueue.offer(expiringKey)
    }

    operator fun get(key: KEY): VALUE? {

        delayQueue.removeExpired()
        expiringKeys[key]?.renew()

        return internalMap[key]
    }

    override fun dispose() {

        internalMap.values.filterIsInstance<Disposable>().forEach{ it.dispose() }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun DelayQueue<ExpiringKey<KEY>>.removeExpired() {

        var delayedKey = this.poll()
        while (delayedKey != null) {

            internalMap.remove(delayedKey.key)
            expiringKeys.remove(delayedKey.key)
            delayedKey = this.poll()
        }
    }

    private fun VALUE.dispose(): VALUE  {

        if (this is Disposable)
            this.dispose()

        return this
    }
}
