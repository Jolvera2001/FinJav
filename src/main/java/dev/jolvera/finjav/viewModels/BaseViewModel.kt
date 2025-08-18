package dev.jolvera.finjav.viewModels

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel {
    protected val isLoading = SimpleBooleanProperty(false)
    protected val errorMessage = SimpleStringProperty("")
    protected val scope = CoroutineScope(Dispatchers.JavaFx + SupervisorJob())

    fun IsLoadingProperty(): BooleanProperty = isLoading
    fun IsErrorProperty(): StringProperty = errorMessage

    protected fun <T> executeAsync(
        backgroundWork: suspend () -> T,
        uiUpdate: (T) -> Unit = {}
    ) {
        scope.launch {
            isLoading.value = true
            errorMessage.value = ""

            try {
                val result = withContext(Dispatchers.IO) {
                    backgroundWork()
                }
                uiUpdate(result)
            } catch (e: Exception) {
                errorMessage.value = e.message ?: "Unknown error"
            } finally {
                isLoading.value = false
            }
        }
    }

    open fun close() {
        scope.cancel()
    }
}