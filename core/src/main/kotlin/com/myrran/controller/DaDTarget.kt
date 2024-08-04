package com.myrran.controller

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target
import com.myrran.domain.Identifiable

interface DaDTarget<ID>: Identifiable<ID> {

    fun getTarget(): Target
}
