package com.webapps.taskflow.mapper;

import com.webapps.taskflow.dtos.user.UserCreateRequest;
import com.webapps.taskflow.dtos.user.UserResponse;
import com.webapps.taskflow.dtos.user.UserUpdateRequest;
import com.webapps.taskflow.entity.User;

public final class UserMapper {
    private UserMapper() {}

    public static User toEntity(UserCreateRequest request){
        return new User(request.email(), request.name());
    }

    public static UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static void applyUpdate(UserUpdateRequest request, User user) {
        user.setEmail(request.email());
        user.setName(request.name());
    }
}
