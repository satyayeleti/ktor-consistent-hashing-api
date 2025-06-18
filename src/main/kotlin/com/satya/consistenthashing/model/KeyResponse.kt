package com.satya.consistenthashing.model

import kotlinx.serialization.Serializable

@Serializable
data class KeyResponse(
        val node: String,
        val metadata: Map<String, String>
)
