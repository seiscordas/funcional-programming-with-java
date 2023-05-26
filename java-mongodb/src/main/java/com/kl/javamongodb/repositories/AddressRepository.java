package com.kl.javamongodb.repositories;

import com.kl.javamongodb.entities.Address;
import com.kl.javamongodb.entities.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {
    List<Address> findAllByPerson(Person person);
}
