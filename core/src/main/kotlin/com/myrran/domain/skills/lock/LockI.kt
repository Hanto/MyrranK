package com.myrran.domain.skills.lock

interface LockI
{
    fun isOpenedBy(keys: Collection<LockType>): Boolean
}
