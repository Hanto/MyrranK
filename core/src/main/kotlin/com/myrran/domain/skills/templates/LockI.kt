package com.myrran.domain.skills.templates

interface LockI
{
    fun isOpenedBy(keys: Collection<LockType>): Boolean
}
