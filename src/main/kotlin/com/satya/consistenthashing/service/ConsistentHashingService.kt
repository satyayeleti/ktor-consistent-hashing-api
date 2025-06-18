package com.example.consistenthashing.service

import java.math.BigInteger
import java.security.MessageDigest
import java.util.*

class ConsistentHashingService {
    private val ring = TreeMap<BigInteger, String>()
    private val nodeHashes = mutableMapOf<String, BigInteger>()
    private val keyToNode = mutableMapOf<String, String>()
    private val dataStore = mutableMapOf<String, MutableMap<String, Map<String, String>>>()
    private val digest = MessageDigest.getInstance("MD5")

    private fun hashKey(key: String): BigInteger {
        val bytes = digest.digest(key.toByteArray())
        return BigInteger(1, bytes)
    }

    fun addNode(nodeId: String) {
        val newNodeHash = hashKey(nodeId)

        // Insert the new node into the ring and maps
        ring[newNodeHash] = nodeId
        nodeHashes[nodeId] = newNodeHash
        dataStore[nodeId] = mutableMapOf()

        // If this is the only node, no need to rebalance
        if (ring.size == 1) return

        // Rebalance: move keys that now fall under this new node
        val allKeysToCheck = dataStore.flatMap { it.value.keys }
        for (key in allKeysToCheck) {
            val correctNode = getNodeForKey(key)
            if (correctNode == nodeId) {
                // Key should belong to the new node now
                val oldNode = dataStore.entries.firstOrNull { it.value.containsKey(key) }?.key
                if (oldNode != null && oldNode != nodeId) {
                    val value = dataStore[oldNode]?.remove(key)
                    if (value != null) {
                        dataStore[nodeId]?.put(key, value)
                        keyToNode[key] = nodeId
                    }
                }
            }
        }
    }


    fun removeNode(nodeId: String) {
        nodeHashes.remove(nodeId)?.let { ring.remove(it) } ?.also {
            dataStore.remove(nodeId)?.forEach { (k, v) ->
                keyToNode.remove(k) // optional cleanup
                getNodeForKey(k)?.let { newNode ->
                    dataStore[newNode]?.put(k, v)
                    keyToNode[k] = newNode
                }
            }
        }
    }

    fun getNodeForKey(key: String): String? {
        if (ring.isEmpty()) return null
        val hash = hashKey(key)
        val tail = ring.tailMap(hash)
        val targetKey = if (tail.isNotEmpty()) tail.firstKey() else ring.firstKey()
        return ring[targetKey]
    }


    fun storeKey(key: String, metadata: Map<String, String>) {
        getNodeForKey(key)?.let { node ->
            dataStore[node]?.put(key, metadata)
            keyToNode[key] = node
        }
    }

    fun getKey(key: String): Pair<String?, Map<String, String>?> {
        val node = keyToNode[key] // Use actual node stored in
        return node to node?.let { dataStore[it]?.get(key) }
    }

    fun getRingState(): Map<String, Map<String, Map<String, String>>> {
        return dataStore
    }
}
