package com.myrran.domain.utils.observer

import com.myrran.domain.events.Event
import java.beans.PropertyChangeListener

interface Observable {

    fun notify(event: Event)
    fun notify(propertyName: String)
    fun notify(propertyName: String, oldValue: Any?, newValue: Any?)
    fun addObserver(observer: PropertyChangeListener)
    fun removeObserver(observer: PropertyChangeListener)
    fun removeAllObservers()
}
