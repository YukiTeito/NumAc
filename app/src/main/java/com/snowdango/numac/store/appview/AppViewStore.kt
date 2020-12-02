package com.snowdango.numac.store.appview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.snowdango.numac.actions.applistdb.DatabaseAction
import com.snowdango.numac.actions.applistdb.DatabaseActionState
import com.snowdango.numac.actions.apprecently.RecentlyAppDatabaseAction
import com.snowdango.numac.actions.apprecently.RecentlyAppDatabaseActionState
import com.snowdango.numac.dispatcher.Dispatcher

class AppViewStore(private val dispatcher: Dispatcher):
        ViewModel(), Dispatcher.DatabaseActionListener, Dispatcher.RecentlyActionListener {

    init {
        dispatcher.registerAppView(this,this)
    }

    val databaseActionData: LiveData<DatabaseActionState> = MutableLiveData<DatabaseActionState>().apply { value = DatabaseActionState.None }
    val recentlyActionData: LiveData<RecentlyAppDatabaseActionState> = MutableLiveData<RecentlyAppDatabaseActionState>().apply { value = RecentlyAppDatabaseActionState.None }


    private fun updateDatabase(action: DatabaseAction) {
        (databaseActionData as MutableLiveData<DatabaseActionState>).value = action.state
    }

    private fun updateRecently(action: RecentlyAppDatabaseAction) {
        (recentlyActionData as MutableLiveData<RecentlyAppDatabaseActionState>).value = action.state
    }

    override fun on(action: RecentlyAppDatabaseAction) = updateRecently(action)

    override fun on(action: DatabaseAction) = updateDatabase(action)

    override fun onCleared() {
        super.onCleared()
        dispatcher.unregisterAppView(this,this)
    }

}