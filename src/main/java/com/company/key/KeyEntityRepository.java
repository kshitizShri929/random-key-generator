package com.company.key;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KeyEntityRepository implements PanacheRepository<KeyEntity> {
    // Custom repository methods can be added here if needed
}
