package com.myrran.domain

import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

class Observable(

    private val source: Any,
    private val observed: PropertyChangeSupport = PropertyChangeSupport(source)

): ObservableI {

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun notify(propertyName: String, oldValue: Any?, newValue: Any?) {
        observed.firePropertyChange(propertyName, oldValue, newValue)
    }

    override fun addObserver(observer: PropertyChangeListener) {
        observed.addPropertyChangeListener(observer)
    }

    override fun removeObserver(observer: PropertyChangeListener) {
        observed.removePropertyChangeListener(observer)
    }

    override fun removeAllObservers() {
        val observers = observed.propertyChangeListeners
        for (observer in observers) observed.removePropertyChangeListener(observer)
    }
}
