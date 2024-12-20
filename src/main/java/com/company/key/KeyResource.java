package com.company.key;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/key")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KeyResource {

    @Inject
    KeyService service;

    // Endpoint to get all keys
    @GET
    public List<KeyEntity> getAllKeys() {
        return service.getAllKeys();
    }

    // Endpoint to generate and save a new key
    @POST
    public KeyEntity generateKey() {
        return service.generateAndSaveKey();
    }

    // Endpoint to verify if a key exists
    @GET
    @Path("/{key}")
    public boolean verifyKey(@PathParam("key") String key) {
        return service.verifyKeyExists(key);
    }
}
