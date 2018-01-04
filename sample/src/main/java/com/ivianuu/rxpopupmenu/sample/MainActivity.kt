package com.ivianuu.rxpopupmenu.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast

import com.ivianuu.rxpopupmenu.RxPopupMenu

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showPopupButton = findViewById<Button>(R.id.show_popup)

        showPopupButton.setOnClickListener { view ->
            disposable = RxPopupMenu.create(view, R.menu.test_menu, Gravity.TOP)
                    .subscribe { menuItem ->
                        Toast.makeText(this@MainActivity, menuItem.title.toString() + " Clicked!",
                                Toast.LENGTH_SHORT).show()
                    }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
