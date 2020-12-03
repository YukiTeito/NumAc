package com.snowdango.numac.dispatcher

import android.util.Log
import com.snowdango.numac.actions.applist.AppListAction
import com.snowdango.numac.actions.applistdb.DatabaseAction
import com.snowdango.numac.actions.apprecently.RecentlyAppDatabaseAction
import com.snowdango.numac.actions.command.CommandAction
import java.util.*

class Dispatcher {

    private val appListListeners = Collections.synchronizedList(mutableListOf<AppListActionListener>())
    private val commandListeners = Collections.synchronizedList(mutableListOf<CommandActionListener>())
    private val databaseListeners = Collections.synchronizedList(mutableListOf<DatabaseActionListener>())
    private val recentlyListeners = Collections.synchronizedList(mutableListOf<RecentlyActionListener>())

    interface AppListActionListener {
        fun on(action: AppListAction)
    }

    interface CommandActionListener{
        fun on(action: CommandAction)
    }

    interface DatabaseActionListener {
        fun on(action: DatabaseAction)
    }

    interface RecentlyActionListener{
        fun on(action: RecentlyAppDatabaseAction)
    }

    fun dispatchDatabase(action: DatabaseAction) {
        Log.d("dispatcher", "database")
        databaseListeners.forEach { it.on(action) }
    }

    fun dispatchRecently(action: RecentlyAppDatabaseAction){
        Log.d("dispatcher", "recently")
        recentlyListeners.forEach { it.on(action) }
    }
    fun dispatchAppList(action: AppListAction) {
        Log.d("dispatcher", "appList")
        appListListeners.forEach { it.on(action) }
    }

    fun dispatchCommand(action: CommandAction){
        Log.d("dispatcher", "command")
        commandListeners.forEach { it.on(action) }
    }

    fun registerMain(listenerAppList: AppListActionListener,
                 listenerCommand: CommandActionListener) {
        Log.d(TAG,"registerMain")
        appListListeners.add(listenerAppList)
        commandListeners.add(listenerCommand)
    }

    fun registerAppView(listenerDatabaseActionListener: DatabaseActionListener,
                        listenerRecentlyActionListener: RecentlyActionListener){
        Log.d(TAG,"registerAppView")
        databaseListeners.add(listenerDatabaseActionListener)
        recentlyListeners.add(listenerRecentlyActionListener)
    }

    fun unregisterMain(listenerAppList: AppListActionListener,
                   listenerCommand: CommandActionListener) {
        Log.d(TAG,"unregisterMain")
        appListListeners.remove(listenerAppList)
        commandListeners.remove(listenerCommand)
    }

    fun unregisterAppView(listenerDatabaseListener: DatabaseActionListener,
                          listenerRecentlyListener: RecentlyActionListener){
        Log.d(TAG,"unregisterAppView")
        databaseListeners.remove(listenerDatabaseListener)
        recentlyListeners.remove(listenerRecentlyListener)
    }

    companion object {
        const val TAG = "Dispatcher"
    }
}