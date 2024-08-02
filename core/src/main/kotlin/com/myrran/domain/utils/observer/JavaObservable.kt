package com.myrran.domain.utils.observer

import com.myrran.domain.events.Event
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

class JavaObservable: Observable {

    private val observed: PropertyChangeSupport = PropertyChangeSupport(this)

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun notify(event: Event) =
        observed.firePropertyChange(event::class.simpleName, null, event)

    override fun notify(propertyName: String)  =
        observed.firePropertyChange(propertyName, null, null)

    override fun notify(propertyName: String, oldValue: Any?, newValue: Any?) =
        observed.firePropertyChange(propertyName, oldValue, newValue)

    override fun addObserver(observer: PropertyChangeListener) =
        observed.addPropertyChangeListener(observer)

    override fun removeObserver(observer: PropertyChangeListener) =
        observed.removePropertyChangeListener(observer)

    override fun removeAllObservers() =
        observed.propertyChangeListeners.forEach { observed.removePropertyChangeListener(it) }
}
