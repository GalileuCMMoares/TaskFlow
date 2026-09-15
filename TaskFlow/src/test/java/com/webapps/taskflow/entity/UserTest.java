package com.webapps.taskflow.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void softDeleteSetsDeletedAt() {
        User user = new User("ana@taskflow.dev", "Ana Souza");

        user.softDelete();

        assertThat(user.getDeletedAt()).isNotNull();
    }
}
