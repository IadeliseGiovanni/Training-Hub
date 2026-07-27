package com.traininghub.participant_service.repository;

import com.traininghub.participant_service.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    Optional<Participant> findByTaxCode(String taxCode);
    Optional<Participant> findByEmail(String email);

    boolean existsByTaxCode(String taxCode);
    boolean existsByEmail(String email);
}