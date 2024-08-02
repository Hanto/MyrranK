package com.myrran.domain.utils.observer

import com.myrran.domain.events.Event
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

interface Observer: PropertyChangeListener {

    fun update(event: Event)
    override fun propertyChange(evt: PropertyChangeEvent) = update(evt.newValue as Event)
}
