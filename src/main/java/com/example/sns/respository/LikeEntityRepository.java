package com.example.sns.respository;

import com.example.sns.model.entity.LikeEntity;
import com.example.sns.model.entity.PostEntity;
import com.example.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    @Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post =:post")
    Integer countByPost(PostEntity post);
    List<LikeEntity> findAllByPost(PostEntity postEntity);
}
