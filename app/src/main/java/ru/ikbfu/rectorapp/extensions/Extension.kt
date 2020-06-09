package ru.ikbfu.rectorapp.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.CheckResult
import androidx.annotation.LayoutRes
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit

@CheckResult
fun <T> Observable<T>.subscribeBy(
    onNext: (T) -> Unit,
    onError: (Throwable) -> Unit = { Timber.e(it) }
): Disposable = subscribe(onNext, onError)

fun Navigator.setLaunchScreen(screen: SupportAppScreen) {
    applyCommands(
        arrayOf(
            BackTo(null),
            Replace(screen)
        )
    )
}

@CheckResult
fun View.rxOnClickListener(onClick: () -> Unit) =
    RxView.clicks(this)
        .debounce(500L, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy({ onClick() })

@CheckResult
fun TextView.rxTextChanges(onEdit: (message: String) -> Unit) =
    RxTextView.textChanges(this)
        .skipInitialValue()
        .debounce(500L, TimeUnit.MILLISECONDS)
        .map { it.toString() }
        .distinctUntilChanged()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy({ message -> onEdit(message) })

@CheckResult
fun TextView.rxInputTextChanges(onEdit: (message: String) -> Unit) =
    RxTextView.textChanges(this)
        .skipInitialValue()
        .debounce(50L, TimeUnit.MILLISECONDS)
        .map { it.toString() }
        .distinctUntilChanged()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy({ message -> onEdit(message) })

fun ImageView.loadUserImg(height: Int) =
    Picasso.with(context)
        .load("https://placekitten.com/300/$height")
        .fit()
        .centerCrop()
        .into(this)

fun ViewGroup.inflate(@LayoutRes layout: Int): View =
    LayoutInflater.from(context).inflate(layout, this, false)

val unifyReplaceStrings: Map<String, String> = mapOf(

    " " to "", "." to "", "," to "", ";" to "", "_" to "", "!" to "", "\"" to "",
    ":" to "", "?" to "", "*" to "", "(" to "", ")" to "", "&" to "", "\'" to "",
    "@" to "", "%" to "", "#" to "", "^" to "", "$" to "", "~" to "", "«" to "",
    "»" to "", "<i>" to "", "</i>" to "", "-" to "", "/" to "", "\r" to "", "\n" to "", "ё" to "е"
)

fun String.unify(): String =
    Regex("[^A-Za-z0-9А-Яа-яЁё]").replace(this.toLowerCase(Locale.getDefault()), "")


fun EditText.onImeActionDoneClicks(): Observable<Unit> {
    return Observable.create { emitter ->
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                emitter.onNext(Unit)
                true
            } else {
                false
            }
        }
    }
}

fun View.hideKeyboardOnTouchOutside(context: Context?){
    this.setOnTouchListener { v, event ->
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
