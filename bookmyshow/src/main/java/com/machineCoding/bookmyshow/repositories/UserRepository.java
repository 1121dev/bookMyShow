package com.machineCoding.bookmyshow.repositories;

import com.machineCoding.bookmyshow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    //@Override
    //Optional<User> findById(Long aLong);
    //findByEmail

    @Override
    Optional<User> findById(String s);

    @Override
    User save(User user);
}
