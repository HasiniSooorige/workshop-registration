
package com.kenora.workshop_service.repository;

import com.kenora.workshop_service.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    long countByWorkshop_IdAndStatus(
        Long workshopId,
        Registration.RegistrationStatus status
    );
}
