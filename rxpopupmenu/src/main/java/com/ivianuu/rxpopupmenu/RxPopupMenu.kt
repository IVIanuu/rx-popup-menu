/*
 * Copyright 2017 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.rxpopupmenu

import android.support.annotation.CheckResult
import android.support.annotation.MenuRes
import android.support.v7.widget.PopupMenu
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import io.reactivex.Maybe

/**
 * Factory to create [Maybe]'s of [MenuItem] clicks
 */
object RxPopupMenu {

    /**
     * Emits the clicked [MenuItem] or completes on dismiss
     */
    @JvmStatic
    @JvmOverloads
    @CheckResult
    fun create(anchor: View,
               @MenuRes menuRes: Int,
               gravity: Int = Gravity.NO_GRAVITY,
               preparer: (Menu) -> Unit = stubMenuPreparer
    ): Maybe<MenuItem> {
        val popupMenu = PopupMenu(anchor.context, anchor, gravity).apply {
            inflate(menuRes)
            preparer(this.menu)
        }

        return create(popupMenu)
    }

    /**
     * Emits the clicked [MenuItem] or completes on dismiss
     */
    @JvmStatic
    @CheckResult
    fun create(popupMenu: PopupMenu): Maybe<MenuItem> {
        return Maybe.create { e ->
            // success on clicks
            popupMenu.setOnMenuItemClickListener { item ->
                e.onSuccess(item)
                e.onComplete()
                true
            }

            // complete on dismiss
            popupMenu.setOnDismissListener { e.onComplete() }

            // dismiss pop up on dispose
            e.setCancellable { popupMenu.dismiss() }

            // show
            popupMenu.show()
        }
    }
}

/** no op preparer **/
private val stubMenuPreparer: (Menu) -> Unit = {
}
