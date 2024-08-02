package com.myrran.domain

import com.myrran.domain.events.Event
import java.beans.PropertyChangeListener

interface ObservableI {

    fun notify(event: Event)
    fun notify(propertyName: String)
    fun notify(propertyName: String, oldValue: Any?, newValue: Any?)
    fun addObserver(observer: PropertyChangeListener)
    fun removeObserver(observer: PropertyChangeListener)
    fun removeAllObservers()
}
