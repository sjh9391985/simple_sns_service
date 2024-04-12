package com.example.sns.service;

import com.example.sns.exception.SnsApplicationException;
import com.example.sns.fixture.UserEntityFixture;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.respository.UserEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 회원가입_정상작동() {
        String username = "username";
        String password = "password";

        // mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        Assertions.assertDoesNotThrow(() -> userService.join(username, password));
    }

    @Test
    void 회원가입시_이미_가입한경우() {
        String username = "username";
        String password = "password";

        // mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(mock(UserEntity.class)));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.join(username, password));
    }

    @Test
    void 로그인_정상작동() {
        String username = "username";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(username, password);

        // mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(fixture));

        Assertions.assertDoesNotThrow(() -> userService.login(username, password));
    }

    @Test
    void 로그인시_미회원가입인경우() {
        String username = "username";
        String password = "password";

        // mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(username, password));
    }

    @Test
    void 로그인시_패스워드_틀린경우() {
        String username = "username";
        String password = "password";

        String wrongpassword = "wrongpassword";

        UserEntity fixture = UserEntityFixture.get(username, password);

        // mocking
        when(userEntityRepository.findByUserName(username)).thenReturn(Optional.of(fixture));

        Assertions.assertThrows(SnsApplicationException.class, () -> userService.login(username, wrongpassword));
    }

}
