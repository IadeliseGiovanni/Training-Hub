package com.traininghub.participant_service.Service;

import com.traininghub.participant_service.dto.ParticipantDto;
import com.traininghub.participant_service.model.Participant;
import com.traininghub.participant_service.repository.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    public List<ParticipantDto> getAllParticipants() {
        return participantRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ParticipantDto getParticipantById(Long id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partecipante con ID " + id + " non trovato."));
        return toDto(participant);
    }

    public ParticipantDto createParticipant(ParticipantDto dto) {
        if (participantRepository.existsByTaxCode(dto.getTaxCode())) {
            throw new RuntimeException("Esiste già un partecipante con questo codice fiscale.");
        }
        if (participantRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Esiste già un partecipante con questa email.");
        }

        Participant participant = toModel(dto);
        Participant saved = participantRepository.save(participant);
        return toDto(saved);
    }

    public ParticipantDto updateParticipant(Long id, ParticipantDto dto) {
        Participant existing = participantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partecipante con ID " + id + " non trovato."));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setPhone(dto.getPhone());
        existing.setEducationLevel(dto.getEducationLevel());
        existing.setEmploymentStatus(dto.getEmploymentStatus());

        if (!existing.getEmail().equals(dto.getEmail()) && participantRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("L'email è già utilizzata da un altro partecipante.");
        }
        existing.setEmail(dto.getEmail());

        if (!existing.getTaxCode().equals(dto.getTaxCode()) && participantRepository.existsByTaxCode(dto.getTaxCode())) {
            throw new RuntimeException("Il codice fiscale è già utilizzato da un altro partecipante.");
        }
        existing.setTaxCode(dto.getTaxCode());

        existing.setBirthDate(dto.getBirthDate());

        Participant updated = participantRepository.save(existing);
        return toDto(updated);
    }

    public void deleteParticipant(Long id) {
        if (!participantRepository.existsById(id)) {
            throw new RuntimeException("Impossibile eliminare: partecipante non trovato.");
        }
        participantRepository.deleteById(id);
    }

    private ParticipantDto toDto(Participant p) {
        ParticipantDto dto = new ParticipantDto();
        dto.setId(p.getId());
        dto.setFirstName(p.getFirstName());
        dto.setLastName(p.getLastName());
        dto.setTaxCode(p.getTaxCode());
        dto.setBirthDate(p.getBirthDate());
        dto.setEmail(p.getEmail());
        dto.setPhone(p.getPhone());
        dto.setEducationLevel(p.getEducationLevel());
        dto.setEmploymentStatus(p.getEmploymentStatus());
        return dto;
    }

    private Participant toModel(ParticipantDto dto) {
        Participant p = new Participant();
        p.setId(dto.getId());
        p.setFirstName(dto.getFirstName());
        p.setLastName(dto.getLastName());
        p.setTaxCode(dto.getTaxCode());
        p.setBirthDate(dto.getBirthDate());
        p.setEmail(dto.getEmail());
        p.setPhone(dto.getPhone());
        p.setEducationLevel(dto.getEducationLevel());
        p.setEmploymentStatus(dto.getEmploymentStatus());
        return p;
    }
}