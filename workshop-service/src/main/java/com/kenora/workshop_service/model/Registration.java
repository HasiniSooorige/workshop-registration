
package com.kenora.workshop_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "workshop_id")
    private Workshop workshop;

    @Column(nullable = false)
    private String attendeeName;

    @Column(nullable = false)
    private String attendeeEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus status =
            RegistrationStatus.ACTIVE;

    @ManyToOne(optional = false)
    @JoinColumn(name = "registered_by")
    private User registeredBy;

    private LocalDateTime registeredAt;
    private LocalDateTime cancelledAt;

    @ManyToOne
    @JoinColumn(name = "cancelled_by")
    private User cancelledBy;

    public enum RegistrationStatus {
        ACTIVE, CANCELLED
    }

    public Registration() {}

    @PrePersist
    public void onCreate() {
        registeredAt = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public Workshop getWorkshop() { return workshop; }
    public void setWorkshop(Workshop workshop) {
        this.workshop = workshop;
    }

    public String getAttendeeName() { return attendeeName; }
    public void setAttendeeName(String attendeeName) {
        this.attendeeName = attendeeName;
    }

    public String getAttendeeEmail() { return attendeeEmail; }
    public void setAttendeeEmail(String attendeeEmail) {
        this.attendeeEmail = attendeeEmail;
    }

    public RegistrationStatus getStatus() { return status; }
    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public User getRegisteredBy() { return registeredBy; }
    public void setRegisteredBy(User registeredBy) {
        this.registeredBy = registeredBy;
    }

    public LocalDateTime getRegisteredAt() { return registeredAt; }

    public LocalDateTime getCancelledAt() { return cancelledAt; }
    public void setCancelledAt(LocalDateTime cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public User getCancelledBy() { return cancelledBy; }
    public void setCancelledBy(User cancelledBy) {
        this.cancelledBy = cancelledBy;
    }
}
