package com.example.sns.model.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AlarmEntityRepository extends JpaRepository<AlarmEntity, Integer> {
    Page<AlarmEntity> findAllByUserId(Integer userId, Pageable pageable);
}