package com.myrran.controller

import com.myrran.badlogic.Source
import com.myrran.domain.Identifiable

interface DaDSource<ID>: Identifiable<ID> {

    fun getSource(): Source
}
