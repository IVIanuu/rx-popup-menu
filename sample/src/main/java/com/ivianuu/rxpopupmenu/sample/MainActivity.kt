package com.ivianuu.rxpopupmenu.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.ivianuu.rxpopupmenu.RxPopupMenu
import io.reactivex.Observable

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showPopupButton = findViewById<Button>(R.id.show_popup)

        disposable = showPopupButton.clicks()
            .flatMapMaybe { RxPopupMenu.create(it, R.menu.test_menu, Gravity.TOP) }
            .subscribe {
                Toast.makeText(this, it.title.toString() + " Clicked!",
                    Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    private fun View.clicks(): Observable<View> {
        return Observable.create { e ->
            e.setCancellable { setOnClickListener(null) }

            setOnClickListener {
                if (!e.isDisposed) {
                    e.onNext(it)
                }
            }
        }
    }
}
