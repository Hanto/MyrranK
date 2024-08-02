package com.myrran.domain.skills.templates

import java.util.Collections

data class Lock(

    val openedBy: Collection<LockType>

): LockI
{
    override fun isOpenedBy(keys: Collection<LockType>): Boolean =

        !Collections.disjoint(openedBy, keys)
}
