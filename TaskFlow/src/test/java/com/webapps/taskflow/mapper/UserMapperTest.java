package com.webapps.taskflow.mapper;

import com.webapps.taskflow.dtos.user.UserCreateRequest;
import com.webapps.taskflow.dtos.user.UserResponse;
import com.webapps.taskflow.dtos.user.UserUpdateRequest;
import com.webapps.taskflow.entity.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void toEntityMapsEmailAndNameToTheirOwnFields() {
        UserCreateRequest request = new UserCreateRequest("Ana Souza", "ana@taskflow.dev");

        User user = UserMapper.toEntity(request);

        assertThat(user.getName()).isEqualTo("Ana Souza");
        assertThat(user.getEmail()).isEqualTo("ana@taskflow.dev");
    }

    @Test
    void toResponseMapsAllFields() {
        User user = new User("ana@taskflow.dev", "Ana Souza");

        UserResponse response = UserMapper.toResponse(user);

        assertThat(response.id()).isEqualTo(user.getId());
        assertThat(response.name()).isEqualTo("Ana Souza");
        assertThat(response.email()).isEqualTo("ana@taskflow.dev");
    }

    @Test
    void applyUpdateOverwritesNameAndEmail() {
        User user = new User("old@taskflow.dev", "Old Name");
        UserUpdateRequest request = new UserUpdateRequest("New Name", "new@taskflow.dev");

        UserMapper.applyUpdate(request, user);

        assertThat(user.getName()).isEqualTo("New Name");
        assertThat(user.getEmail()).isEqualTo("new@taskflow.dev");
    }
}
