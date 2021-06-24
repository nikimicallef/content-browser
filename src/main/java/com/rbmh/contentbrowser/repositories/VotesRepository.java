package com.rbmh.contentbrowser.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rbmh.contentbrowser.repositories.models.VoteDbModel;


@Repository
public interface VotesRepository
        extends MongoRepository<VoteDbModel, String> {

}
