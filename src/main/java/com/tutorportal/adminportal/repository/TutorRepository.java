package com.tutorportal.adminportal.repository;

import com.tutorportal.adminportal.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    List<Tutor> findByStatus(String status);
    List<Tutor> findBySubjectContainingIgnoreCase(String subject);

    @Query("SELECT COUNT(t) FROM Tutor t WHERE t.status = 'ACTIVE'")
    long countActiveTutors();
}
