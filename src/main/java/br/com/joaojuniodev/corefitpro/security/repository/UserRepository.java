package br.com.joaojuniodev.corefitpro.security.repository;

import br.com.joaojuniodev.corefitpro.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
    List<User> findAllByRoleName(@Param("roleName") String roleName);

    @Query("SELECT u FROM User u WHERE u.username =:username")
    User findByUserName(@Param("username") String username);

    boolean existsByUsernameAndIdNot(String username, UUID id);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, UUID id);

    @Query("SELECT u.fullName FROM User u WHERE u.username = :username")
    String findFullNameByUser(@Param("username") String username);
}
