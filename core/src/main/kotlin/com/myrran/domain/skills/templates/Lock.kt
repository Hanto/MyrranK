package com.myrran.domain.skills.templates

import java.util.Collections

data class Lock(

    val openedBy: Collection<LockTypes>

): LockI
{
    override fun isOpenedBy(keys: Collection<LockTypes>): Boolean =

        !Collections.disjoint(openedBy, keys)
}
