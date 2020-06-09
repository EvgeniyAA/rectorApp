package ru.ikbfu.rectorapp.global

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface CompositeDisposableComponent {

    val compositeDisposable: CompositeDisposable

    fun Disposable.bind()

    fun clear()
}