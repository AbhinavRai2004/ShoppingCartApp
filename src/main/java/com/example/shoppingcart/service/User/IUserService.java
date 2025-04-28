package com.example.shoppingcart.service.User;

import com.example.shoppingcart.dto.UserDto;
import com.example.shoppingcart.model.User;
import com.example.shoppingcart.request.CreateUserRequest;
import com.example.shoppingcart.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
