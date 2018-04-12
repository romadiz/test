package com.text.demo.repository;

import com.text.demo.model.TextAnalist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextAnalysRepository extends MongoRepository<TextAnalist, String> {
}
