package com.project.MockitoTesting.pro_env;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository{

    Map<String, User> map = new HashMap<>();
    @Override
    public boolean save(User user) {
        boolean res = false;

        if (!map.containsKey(user.getEmail())) {
            map.put(user.getEmail(), user);
            res = true;
        }

        return res;
    }
}
