
package com.kenora.workshop_service.service;

import com.kenora.workshop_service.model.*;
import com.kenora.workshop_service.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final WorkshopRepository workshopRepository;
    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;

    public RegistrationService(
            WorkshopRepository workshopRepository,
            RegistrationRepository registrationRepository,
            UserRepository userRepository) {
        this.workshopRepository = workshopRepository;
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Registration register(
            Long workshopId,
            String attendeeName,
            String attendeeEmail,
            Long staffId) {

        Workshop workshop = workshopRepository
                .findByIdForUpdate(workshopId)
                .orElseThrow(() ->
                    new RuntimeException("Workshop not found"));

        if (!"SCHEDULED".equals(workshop.getStatus())) {
            throw new RuntimeException("Workshop is not available");
        }

        long activeCount =
                registrationRepository.countByWorkshop_IdAndStatus(
                    workshopId,
                    Registration.RegistrationStatus.ACTIVE);

        if (activeCount >= workshop.getCapacity()) {
            throw new RuntimeException("Workshop is full");
        }

        User staff = userRepository.findById(staffId)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        Registration registration = new Registration();
        registration.setWorkshop(workshop);
        registration.setAttendeeName(attendeeName);
        registration.setAttendeeEmail(attendeeEmail);
        registration.setRegisteredBy(staff);

        return registrationRepository.save(registration);
    }

    @Transactional
    public Registration cancel(Long registrationId, Long staffId) {
        Registration registration = registrationRepository
                .findById(registrationId)
                .orElseThrow(() ->
                    new RuntimeException("Registration not found"));

        if (registration.getStatus()
                == Registration.RegistrationStatus.CANCELLED) {
            throw new RuntimeException("Already cancelled");
        }

        User staff = userRepository.findById(staffId)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        registration.setStatus(
                Registration.RegistrationStatus.CANCELLED);
        registration.setCancelledBy(staff);
        registration.setCancelledAt(
                java.time.LocalDateTime.now());

        return registrationRepository.save(registration);
    }
}
