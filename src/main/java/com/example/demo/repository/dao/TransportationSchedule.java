package com.example.demo.repository.dao;

import com.example.demo.repository.dao.enums.Day;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "TRANSPORTATION_SCHEDULE")
public class TransportationSchedule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANSPORTATION_SCHEDULE")
    @SequenceGenerator(name = "SEQ_TRANSPORTATION_SCHEDULE", sequenceName = "SEQ_TRANSPORTATION_SCHEDULE", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TRANSPORTATION_ID", nullable = false)
    private Transportation transportation;

    @Enumerated
    @Column(name = "OPERATING_DAY")
    private Day operatingDay;
}
