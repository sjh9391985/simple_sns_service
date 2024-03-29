package com.example.sns.respository;

import com.example.sns.model.entity.PostEntity;
import com.example.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostEntityRepository extends JpaRepository<PostEntity, Integer> {

}
