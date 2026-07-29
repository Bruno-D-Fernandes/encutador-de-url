package edu.encurtaUrl.repository;

import edu.encurtaUrl.model.UserBa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBaRepository extends JpaRepository<UserBa, String> {

}
