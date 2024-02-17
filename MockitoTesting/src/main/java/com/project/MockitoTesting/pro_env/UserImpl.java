package com.project.MockitoTesting.pro_env;

import com.project.MockitoTesting.pro_env.UInterface;
import com.project.MockitoTesting.pro_env.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserImpl implements UInterface {

    UserRepository userRepository;
    EmailVerificationService emailVerificationService;

    @Override
    public User createUser(String name, int age, String email) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("User's name should be not empty !!!");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("User's email should be not empty !!!");
        }
        if (age < 16) {
            throw new IllegalArgumentException("User's age should be greater than 15 !!!");
        }

        User user = new User(name, age, email);
        boolean created;
        try {
            created = userRepository.save(user);
        }catch (Exception e) {
            throw new UserNotCreatedException("FakeUser is not created  !!!");
        }

        if (!created) {
            throw new UserNotCreatedException("User is not created !!!");
        }

        try {
            emailVerificationService.scheduleEmailConfirmation(user);
        }catch (Exception exception) {
            throw new UserNotCreatedException(exception.getMessage());
        }

        return user;
    }
}
