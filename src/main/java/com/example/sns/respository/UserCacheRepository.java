package com.example.sns.respository;

import com.example.sns.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private final RedisTemplate<String, User> userRedisTemplate;
    private final static Duration USER_CACHE_TIL = Duration.ofDays(3);

    public void setUser(User user) {
        userRedisTemplate.opsForValue().set(getKey(user.getUsername()), user);
    }

    public Optional<User> getUser(String userName) {
        User user = userRedisTemplate.opsForValue().get(getKey(userName));
        return Optional.ofNullable(user);
    }

    private String getKey(String userName) {
        return "USER : " + userName;
    }

}
