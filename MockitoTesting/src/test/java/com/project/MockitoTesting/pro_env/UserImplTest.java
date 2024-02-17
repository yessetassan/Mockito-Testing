package com.project.MockitoTesting.pro_env;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserImplTest {


    @InjectMocks UserImpl userImpl;

    @Mock UserRepository userRepository;
    @Mock EmailVerificationServiceImpl emailVerificationService;
    String name;
    String email;
    String expectedMessage;
    int age;

    @BeforeEach
    void setUp() {
        name = "Assan";
        email = "assan@gmail.com";
        age = 20;
    }

    @Test
    void testCreateUser_whenOneOfElementIsMissing_thenReturnException() {
        // given
        when(userRepository.save(any(User.class))).thenReturn(true);

        // when
        User user = userImpl.createUser(name,age,email);

        // then
        assertNotNull(user, "The createUser() method not return null...");
        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void testCreateUser_whenOneOfElementIsMissing_thenReturnException2() {
        // given
        when(userRepository.save(Mockito.any(User.class))).thenReturn(true);

        // when
        User user = userImpl.createUser(name,age,email);

        // then
        assertNotNull(user, "The createUser() method not return null...");
    }

    @Test
    void testCreateUser_checkForUserUserNotCreatedException() {
        // given
        when(userRepository.save(any(User.class))).thenThrow();

        // when & then
        assertThrows(UserNotCreatedException.class, () -> {
            userImpl.createUser(name,age,email);
        },"Must u return UserNotCreatedException...");

    }

    @Test
    void testCreateUser_whenEmailVerificationExceptionThrown_throwsUserServiceException() {

        when(userRepository.save(any(User.class))).thenReturn(true);
        doThrow(UserNotCreatedException.class)
                .when(emailVerificationService)
                .scheduleEmailConfirmation(any(User.class));


        assertThrows(UserNotCreatedException.class,() -> {
            userImpl.createUser(name,age,email);
        },"Should thrown UserServiceException...");

        verify(emailVerificationService, times(1))
                .scheduleEmailConfirmation(any(User.class));
    }

    @Test
    void testCreateUser_whenEmailVerificationCalled_linkToRealObject() {

        when(userRepository.save(any(User.class))).thenReturn(true);
        doCallRealMethod()
                .when(emailVerificationService)
                .scheduleEmailConfirmation(any(User.class));

        userImpl.createUser(name, age,email);

        verify(emailVerificationService,times(1))
                .scheduleEmailConfirmation(any(User.class));
    }
}