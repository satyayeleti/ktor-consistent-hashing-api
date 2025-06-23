# ktor-consistent-hashing-api
Consistent Hashing pet project

# Ktor Consistent Hashing API

## 🚀 Quick Start
check temp.txt for curl commands to create node, post keys and delete node

### Prerequisites
- Java 17+
- Gradle wrapper included

### Run
```bash
./gradlew run

###
This project implements a consistent hashing API using Ktor, a Kotlin framework for building web applications. Here's a breakdown of its functionality:

### Key Features:
1. **Consistent Hashing**:
   - The `ConsistentHashingService` class manages a consistent hashing ring using a `TreeMap` to map hashed values to nodes.
   - It ensures that keys are distributed across nodes in a way that minimizes rebalancing when nodes are added or removed.

2. **Node Management**:
   - **Add Node**: Adds a new node to the ring and redistributes keys that should now belong to the new node.
   - **Remove Node**: Removes a node from the ring and redistributes its keys to other nodes.

3. **Key Management**:
   - **Store Key**: Stores a key and its metadata in the appropriate node based on the consistent hashing algorithm.
   - **Get Key**: Retrieves the metadata for a key and identifies the node where it is stored.

4. **Ring State**:
   - Provides the current state of the ring, showing which keys are stored in which nodes.

### API Endpoints:
The `Application.kt` file sets up the Ktor server and registers routes using the `registerRoutes` function. These routes likely include:
- **POST `/keys`**: To store keys and their metadata.
- **POST `/nodes`**: To add a new node to the ring.
- **DELETE `/nodes/{nodeId}`**: To remove a node from the ring.

### Example Workflow:
1. **Add Nodes**:
   - Use the `curl` commands in `temp.txt` to add nodes (`node1`, etc.).
2. **Store Keys**:
   - Post keys with metadata to the `/keys` endpoint.
3. **Remove Nodes**:
   - Delete a node using the `/nodes/{nodeId}` endpoint, triggering redistribution of its keys.

### Consistent Hashing Benefits:
- Reduces the impact of node addition/removal on the overall system.
- Ensures even distribution of keys across nodes.

This project is a demonstration of consistent hashing principles applied in a distributed system context.

