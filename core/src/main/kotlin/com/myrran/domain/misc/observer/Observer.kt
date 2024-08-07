package com.myrran.domain.misc.observer

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

interface Observer<EVENT>: PropertyChangeListener {

    fun propertyChange(event: EVENT)
    override fun propertyChange(evt: PropertyChangeEvent) = propertyChange(evt.newValue as EVENT)
}
