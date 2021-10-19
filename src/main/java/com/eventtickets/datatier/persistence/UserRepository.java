package com.eventtickets.datatier.persistence;

import com.eventtickets.datatier.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long>
{
  User findByEmail(String email);
}
