package com.mv1998.medronic.core

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android

actual fun httpClientEngine(): HttpClientEngine {
    return Android.create()
}