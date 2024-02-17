package com.project.MockitoTesting.pro_env;

public class EmailVerificationServiceImpl implements EmailVerificationService{
    @Override
    public void scheduleEmailConfirmation(User user) {
        System.out.println(user);
        System.out.println("Printed Object");
    }
}
