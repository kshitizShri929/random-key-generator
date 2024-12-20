package com.company.key;

import io.quarkus.redis.client.RedisClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class KeyService {

    @Inject
    KeyEntityRepository repository;

    @Inject
    RedisClient redisClient;  // Inject RedisClient to interact with Redis (Valkey)

    private final Random random = new Random();

    // Method to generate and save the key
    @Transactional
    public KeyEntity generateAndSaveKey() {
        String key = generateRandomKey();
        KeyEntity keyEntity = new KeyEntity();
        keyEntity.setKey(key);
        keyEntity.setValue(LocalDateTime.now());
        repository.persist(keyEntity);  // Save the entity to the database

        // Save the key-value pair in Redis (Valkey)
        redisClient.set(List.of(key, keyEntity.getValue().toString()));  // Store key-value in Redis

        return keyEntity;
    }

    // Method to verify if a key exists in the repository (Redis & Postgres)
    public boolean verifyKeyExists(String key) {
        // First, check if the key exists in Redis
        List<String> value = redisClient.get(List.of(key));
        if (value != null && !value.isEmpty()) {
            return true;  // Key found in Redis
        }

        // If not found in Redis, check in PostgreSQL database
        return repository.find("key", key).count() > 0;
    }

    // Method to get all keys from the repository (only from the database)
    public List<KeyEntity> getAllKeys() {
        return repository.listAll();  // Fetch all records from the database
    }

    // Helper method to generate a random 12-digit key
    private String generateRandomKey() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append(random.nextInt(10));  // Generate random digits
        }
        return sb.toString();
    }
}
