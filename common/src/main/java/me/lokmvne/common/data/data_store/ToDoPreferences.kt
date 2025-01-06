package me.lokmvne.common.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import me.lokmvne.common.data.data_store.ToDoPreferences.Keys.isFirstTime
import me.lokmvne.common.utils.OrderType
import me.lokmvne.common.utils.ToDoOrder
import me.lokmvne.common.utils.ToDoTheme
import javax.inject.Inject

val Context.toDoPreferences: DataStore<Preferences> by preferencesDataStore(name = "toDoPreferences")

class ToDoPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val todoPreferences = context.toDoPreferences

    private object Keys {
        val toDoOrder = stringPreferencesKey("toDoOrder")
        val theme = stringPreferencesKey("theme")
        val isFirstTime = booleanPreferencesKey("isFirstTime")
    }

    suspend fun setFirstTime() {
        todoPreferences.edit { preferences ->
            preferences[Keys.isFirstTime] = false
        }
    }

    fun getFirstTime(): Flow<Boolean> {
        return todoPreferences.data
            .catch { }
            .map {
                it[Keys.isFirstTime] ?: true
            }
    }

    suspend fun setTheme(theme: ToDoTheme) {
        todoPreferences.edit { preferences ->
            preferences[Keys.theme] = theme.name
        }
    }

    fun readTheme(): Flow<ToDoTheme> {
        return todoPreferences.data
            .catch { }
            .map {
                ToDoTheme.valueOf(it[Keys.theme] ?: ToDoTheme.SYSTEM.name)
            }
    }

    suspend fun setToDoOrderType(todorOrderType: ToDoOrder) {
        todoPreferences.edit { preferences ->
            preferences[Keys.toDoOrder] = serializeToDoOrder(todorOrderType)
        }
    }

    fun readToDoOrderType(): Flow<ToDoOrder> {
        return todoPreferences.data
            .catch {}
            .map {
                deserializeToDoOrder(it[Keys.toDoOrder] ?: "Priority:Descending")
            }
    }

    private fun serializeToDoOrder(toDoOrder: ToDoOrder): String {
        var toDoOrderString = ""
        var orderTypeString = ""

        when (toDoOrder) {
            is ToDoOrder.Priority -> {
                toDoOrderString = "Priority"
            }

            is ToDoOrder.Title -> {
                toDoOrderString = "Title"
            }

            is ToDoOrder.TriggerTime -> {
                toDoOrderString = "TriggerTime"
            }
        }

        when (toDoOrder.orderType) {
            OrderType.Ascending -> {
                orderTypeString = "Ascending"
            }

            OrderType.Descending -> {
                orderTypeString = "Descending"
            }
        }
        return "$toDoOrderString:$orderTypeString"
    }

    private fun deserializeToDoOrder(toDoOrderString: String): ToDoOrder {
        val params = (toDoOrderString).split(":")
        val toDoOrder = params.first()
        val orderType = params.last()
        return when (orderType) {
            "Ascending" -> {
                when (toDoOrder) {
                    "Title" -> {
                        ToDoOrder.Title(OrderType.Ascending)
                    }

                    "TriggerTime" -> {
                        ToDoOrder.TriggerTime(OrderType.Ascending)
                    }

                    else -> {
                        ToDoOrder.Priority(OrderType.Ascending)
                    }
                }
            }

            else -> {
                when (toDoOrder) {
                    "Title" -> {
                        ToDoOrder.Title(OrderType.Descending)
                    }

                    "TriggerTime" -> {
                        ToDoOrder.TriggerTime(OrderType.Descending)
                    }

                    else -> {
                        ToDoOrder.Priority(OrderType.Descending)
                    }
                }
            }
        }
    }
}