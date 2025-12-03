package com.example.demo.repository.specification;

import com.example.demo.controller.dto.RouteCriteriaDto;
import com.example.demo.repository.dao.Transportation;
import com.example.demo.repository.dao.enums.TransportationType;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import static com.example.demo.core.util.SpecificationUtil.getJoin;


public class RouteSpecification {

    private RouteSpecification() {
    }

    public static final String PREVIOUS_LEGS = "previousLegs";
    public static final String NEXT_LEGS = "nextLegs";
    public static final String SCHEDULES = "schedules";
    public static final String OPERATING_DAY = "operatingDay";
    public static final String TYPE = "type";
    public static final String ORIGIN_LOCATION_CODE = "originLocationCode";
    public static final String DESTINATION_LOCATION_CODE = "destinationLocationCode";

    public static Specification<Transportation> buildForRoutes(RouteCriteriaDto criteria) {
        return defineJoins(criteria)
                .and(flightSpec(criteria))
                .and(routeSpec(criteria));
    }

    private static Specification<Transportation> defineJoins(RouteCriteriaDto criteria) {
        return (root, query, cb) -> {
            Join<Transportation, Transportation> preJoin = root.join(PREVIOUS_LEGS, JoinType.LEFT);
            Predicate preOriginPredicate = cb.equal(preJoin.get(ORIGIN_LOCATION_CODE), criteria.from());
            Predicate preNotFlightPredicate = cb.not(cb.equal(preJoin.get(TYPE), TransportationType.FLIGHT));
            preJoin.on(preOriginPredicate, preNotFlightPredicate);
            preJoin.alias(PREVIOUS_LEGS);

            Join<Transportation, Transportation> postJoin = root.join(NEXT_LEGS, JoinType.LEFT);
            Predicate postDestinationPredicate = cb.equal(postJoin.get(DESTINATION_LOCATION_CODE), criteria.to());
            Predicate postNotFlightPredicate = cb.not(cb.equal(postJoin.get(TYPE), TransportationType.FLIGHT));
            postJoin.on(postDestinationPredicate, postNotFlightPredicate);
            postJoin.alias(NEXT_LEGS);

            root.join(SCHEDULES, JoinType.LEFT).alias(SCHEDULES);

            preJoin.join(SCHEDULES, JoinType.LEFT).alias(SCHEDULES);

            postJoin.join(SCHEDULES, JoinType.LEFT).alias(SCHEDULES);

            return null;
        };
    }

    private static Specification<Transportation> flightSpec(RouteCriteriaDto criteria) {
        return (root, query, cb) -> {
            Join<Transportation, ?> scheduleJoin = getJoin(root, SCHEDULES);
            return cb.and(
                    cb.equal(root.get(TYPE), TransportationType.FLIGHT),
                    cb.equal(scheduleJoin.get(OPERATING_DAY), criteria.day())
            );
        };
    }

    private static Specification<Transportation> routeSpec(
            RouteCriteriaDto criteria
    ) {
        return (root, query, cb) -> {
            Join<Transportation, ?> preJoin = getJoin(root, PREVIOUS_LEGS);
            Join<Transportation, ?> postJoin = getJoin(root, NEXT_LEGS);
            Join<?, ?> preSchedulesJoin = getJoin(preJoin, SCHEDULES);
            Join<?, ?> postSchedulesJoin = getJoin(postJoin, SCHEDULES);

            Path<String> preOriginPath = preJoin.get(ORIGIN_LOCATION_CODE);
            Path<String> flightOriginPath = root.get(ORIGIN_LOCATION_CODE);

            Path<String> flightDestinationPath = root.get(DESTINATION_LOCATION_CODE);
            Path<String> postDestinationPath = postJoin.get(DESTINATION_LOCATION_CODE);

            Predicate optionalPrePredicate = cb.or(
                    cb.and(cb.isNull(preOriginPath).not(), cb.equal(preSchedulesJoin.get(OPERATING_DAY), criteria.day())),
                    cb.and(cb.isNull(preOriginPath), cb.equal(flightOriginPath, criteria.from()))
            );
            Predicate optionalPostPredicate = cb.or(
                    cb.and(cb.isNull(postDestinationPath).not(), cb.equal(postSchedulesJoin.get(OPERATING_DAY), criteria.day())),
                    cb.and(cb.isNull(postDestinationPath), cb.equal(flightDestinationPath, criteria.to()))
            );

            return cb.and(optionalPrePredicate, optionalPostPredicate);
        };
    }
}
