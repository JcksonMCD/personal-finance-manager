package com.jackson.personal_finance_manager.service;

import com.jackson.personal_finance_manager.dto.userdtos.UserRegistrationDTO;
import com.jackson.personal_finance_manager.model.User;

public interface UserService {
    UserRegistrationDTO createUser(UserRegistrationDTO userRegistrationDTO);

    User getUserById(long userID);

    User editUser(long id, UserRegistrationDTO user);
}
