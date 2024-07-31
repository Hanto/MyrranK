package com.myrran.domain

import java.beans.PropertyChangeListener

interface ObservableI {

    fun notify(propertyName: String)
    fun notify(propertyName: String, oldValue: Any?, newValue: Any?)
    fun addObserver(observer: PropertyChangeListener)
    fun removeObserver(observer: PropertyChangeListener)
    fun removeAllObservers()
}
