package com.example.demo.repository.impl;

import com.example.demo.repository.RouteRepository;
import com.example.demo.repository.dao.Transportation;
import com.example.demo.repository.specification.projection.RouteProjection;
import com.example.demo.core.util.SpecificationUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.repository.specification.RouteSpecification.NEXT_LEGS;
import static com.example.demo.repository.specification.RouteSpecification.PREVIOUS_LEGS;

@Repository
public class RouteRepositoryImpl implements RouteRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RouteProjection> findAllRoutes(Specification<Transportation> spec) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RouteProjection> query = cb.createQuery(RouteProjection.class);
        Root<Transportation> root = query.from(Transportation.class);

        query.where(spec.toPredicate(root, query, cb));

        Join<Transportation, ?> preLegJoin = SpecificationUtil.getJoin(root, PREVIOUS_LEGS);
        Join<Transportation, ?> postLegJoin = SpecificationUtil.getJoin(root, NEXT_LEGS);


        query.select(cb.construct(
                RouteProjection.class,
                root.join("origin", JoinType.LEFT).get("name").alias("flightFrom"),
                root.get("originLocationCode").alias("flightFromCode"),
                root.join("destination", JoinType.LEFT).get("name").alias("flightTo"),
                root.get("destinationLocationCode").alias("flightToCode"),
                preLegJoin.join("origin", JoinType.LEFT).get("name").alias("preFrom"),
                preLegJoin.get("type").alias("preType"),
                postLegJoin.join("destination", JoinType.LEFT).get("name").alias("postTo"),
                postLegJoin.get("type").alias("postType")
        ));


        return entityManager.createQuery(query).getResultList();
    }
}
