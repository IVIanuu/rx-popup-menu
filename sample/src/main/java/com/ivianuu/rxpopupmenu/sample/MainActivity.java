package com.ivianuu.rxpopupmenu.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ivianuu.rxpopupmenu.RxPopupMenu;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private Disposable popupDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showPopupButton = findViewById(R.id.show_popup);

        showPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupDisposable = RxPopupMenu.create(view, R.menu.test_menu, Gravity.TOP)
                        .subscribe(new Consumer<MenuItem>() {
                            @Override
                            public void accept(MenuItem menuItem) throws Exception {
                                Toast.makeText(MainActivity.this, menuItem.getTitle() + " Clicked!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupDisposable != null && !popupDisposable.isDisposed()) {
            popupDisposable.dispose();
            popupDisposable = null;
        }
    }
}
