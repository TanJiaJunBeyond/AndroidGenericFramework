package com.tanjiajun.dadarecycle.utils

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

/**
 * Created by TanJiaJun on 2019-08-07.
 */
open class ObservableViewModel : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) =
            callbacks.add(callback)

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) =
            callbacks.remove(callback)

    fun notifyChange() =
            callbacks.notifyCallbacks(this, 0, null)

    fun notifyPropertyChanged(fieldId: Int) =
            callbacks.notifyCallbacks(this, fieldId, null)

}