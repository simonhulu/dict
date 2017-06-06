package com.company.dict.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.company.dict.model.User;
@Repository
public interface UsersRepository extends MongoRepository<User,String> {

}
