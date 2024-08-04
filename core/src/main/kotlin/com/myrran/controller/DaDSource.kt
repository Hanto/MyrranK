package com.myrran.controller

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source
import com.myrran.domain.Identifiable

interface DaDSource<ID>: Identifiable<ID> {

    fun getSource(): Source
}
