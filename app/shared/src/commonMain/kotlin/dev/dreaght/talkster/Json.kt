package dev.dreaght.talkster

import kotlinx.serialization.json.Json

internal val json =
    Json {
        allowStructuredMapKeys = true
    }
