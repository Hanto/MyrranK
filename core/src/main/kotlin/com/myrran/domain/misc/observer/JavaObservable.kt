package com.myrran.domain.misc.observer

import java.beans.PropertyChangeSupport

class JavaObservable<EVENT>: Observable<EVENT> {

    private val observed: PropertyChangeSupport = PropertyChangeSupport(this)

    // MAIN:
    //--------------------------------------------------------------------------------------------------------

    override fun notify(event: EVENT) =
        observed.firePropertyChange(event!!::class.simpleName, null, event)

    override fun notify(propertyName: String)  =
        observed.firePropertyChange(propertyName, null, null)

    override fun notify(propertyName: String, oldValue: Any?, newValue: Any?) =
        observed.firePropertyChange(propertyName, oldValue, newValue)

    override fun addObserver(observer: Observer<EVENT>) =
        observed.addPropertyChangeListener(observer)

    override fun removeObserver(observer: Observer<EVENT>) =
        observed.removePropertyChangeListener(observer)

    override fun removeAllObservers() =
        observed.propertyChangeListeners.forEach { observed.removePropertyChangeListener(it) }
}
