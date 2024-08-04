package com.myrran.domain.utils.observer

interface Observable<EVENT> {

    fun notify(event: EVENT)
    fun notify(propertyName: String)
    fun notify(propertyName: String, oldValue: Any?, newValue: Any?)
    fun addObserver(observer: Observer<EVENT>)
    fun removeObserver(observer: Observer<EVENT>)
    fun removeAllObservers()
}
