package com.example.demo.repository.dao;

import com.example.demo.repository.dao.enums.TransportationType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "TRANSPORTATION")
public class Transportation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANSPORTATION")
    @SequenceGenerator(name = "SEQ_TRANSPORTATION", sequenceName = "SEQ_TRANSPORTATION", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", length = 10)
    private TransportationType type;

    @Column(name = "ORIGIN_LOCATION_CODE", insertable = false, updatable = false, nullable = false, length = 10)
    private String originLocationCode;

    @Column(name = "DESTINATION_LOCATION_CODE", insertable = false, updatable = false, nullable = false, length = 10)
    private String destinationLocationCode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ORIGIN_LOCATION_CODE", nullable = false)
    private Location origin;

    @ManyToOne(optional = false)
    @JoinColumn(name = "DESTINATION_LOCATION_CODE", nullable = false)
    private Location destination;

    @OneToMany
    @JoinColumn(name = "ORIGIN_LOCATION_CODE", referencedColumnName = "DESTINATION_LOCATION_CODE", insertable = false, updatable = false)
    private Set<Transportation> nextLegs;

    @OneToMany
    @JoinColumn(name = "DESTINATION_LOCATION_CODE", referencedColumnName = "ORIGIN_LOCATION_CODE", insertable = false, updatable = false)
    private Set<Transportation> previousLegs;

    @OneToMany(mappedBy = "transportation")
    private List<TransportationSchedule> schedules;
}
