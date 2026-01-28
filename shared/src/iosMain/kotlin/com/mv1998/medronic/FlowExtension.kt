package com.mv1998.medronic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FlowPublisher<T>(
    private val flow : Flow<T>
) {
    fun collect(
        onEach: (T) -> Unit
    ) : AutoCloseable {
        val scope = CoroutineScope(Dispatchers.Main)
        val job = scope.launch {
            flow.collect {
                onEach(it)
            }
        }
        return AutoCloseable { job.cancel() }
    }
}

fun <T> Flow<T>.asPublisher() : FlowPublisher<T> = FlowPublisher(this)