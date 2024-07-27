package com.myrran.model.skills.templates

interface LockI
{
    fun isOpenedBy(keys: Collection<LockTypes>): Boolean
}
