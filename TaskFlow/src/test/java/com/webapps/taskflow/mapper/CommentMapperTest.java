package com.webapps.taskflow.mapper;

import com.webapps.taskflow.dtos.comment.CommentCreateRequest;
import com.webapps.taskflow.dtos.comment.CommentResponse;
import com.webapps.taskflow.entity.Comment;
import com.webapps.taskflow.entity.Priority;
import com.webapps.taskflow.entity.Status;
import com.webapps.taskflow.entity.Task;
import com.webapps.taskflow.entity.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentMapperTest {

    @Test
    void toEntityMapsContentAuthorAndTask() {
        User author = new User("ana@taskflow.dev", "Ana Souza");
        Task task = new Task("Criar wireframe", author, Status.PENDING, Priority.MEDIUM);
        CommentCreateRequest request = new CommentCreateRequest("Ficou ótimo!", 1L);

        Comment comment = CommentMapper.toEntity(request, author, task);

        assertThat(comment.getContent()).isEqualTo("Ficou ótimo!");
        assertThat(comment.getAuthor()).isEqualTo(author);
        assertThat(comment.getTask()).isEqualTo(task);
        assertThat(comment.getCreatedAt()).isNotNull();
    }

    @Test
    void toResponseMapsAllFields() {
        User author = new User("ana@taskflow.dev", "Ana Souza");
        Task task = new Task("Criar wireframe", author, Status.PENDING, Priority.MEDIUM);
        Comment comment = new Comment("Ficou ótimo!", author, task);

        CommentResponse response = CommentMapper.toResponse(comment);

        assertThat(response.id()).isEqualTo(comment.getId());
        assertThat(response.content()).isEqualTo("Ficou ótimo!");
        assertThat(response.author().email()).isEqualTo("ana@taskflow.dev");
        assertThat(response.createdAt()).isEqualTo(comment.getCreatedAt());
    }
}
