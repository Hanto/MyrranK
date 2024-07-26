package com.myrran.model.skills.templates

import java.util.Collections

data class Lock(
    val openedBy: Collection<LockTypes>
)
{
    fun isOpenedBy(keys: Collection<LockTypes>): Boolean =

        !Collections.disjoint(openedBy, keys)
}
