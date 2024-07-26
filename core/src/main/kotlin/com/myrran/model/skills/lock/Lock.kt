package com.myrran.model.skills.lock

import java.util.Collections

data class Lock(
    val openedBy: Collection<LockTypes>
)
{
    fun isOpenedBy(keys: Collection<LockTypes>): Boolean =

        !Collections.disjoint(openedBy, keys)
}
