package com.myrran.controller

import com.myrran.badlogic.Target
import com.myrran.domain.Identifiable

interface DaDTarget<ID>: Identifiable<ID> {

    fun getTarget(): Target
}
