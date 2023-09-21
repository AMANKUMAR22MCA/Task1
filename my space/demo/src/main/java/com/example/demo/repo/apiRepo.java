package com.example.demo.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.api;

@Repository
public interface apiRepo extends MongoRepository<api,Long> {

}
