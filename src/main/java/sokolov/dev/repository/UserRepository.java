package sokolov.dev.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sokolov.dev.entity.User;
import sokolov.dev.entity.UserRole;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer> {
    List<User> findAllByRoleInOrderById(Iterable<UserRole> roles);
    Optional<User> findByEmailIgnoreCase(String email);

    @Modifying
    @Query("UPDATE User set role = :role WHERE id = :id")
    void updateRole(int id, @Param("role") UserRole newRole);
}
