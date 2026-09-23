import { useEffect, useState } from "react";
import type { FormEvent } from "react";
import { Link, useParams } from "react-router-dom";
import { useTask } from "../hooks/useTask";
import { STATUS_LABELS } from "../types/Task";
import type { Comment } from "../types/Comment";
import { createComment, fetchComments } from "../services/commentsApi";
import { fetchCurrentUser } from "../services/usersApi";
import type { User } from "../types/User";

function TaskDetailPage() {
  const { id } = useParams<{ id: string }>();
  const { task, loading, error } = useTask(id!);
  const [comments, setComments] = useState<Comment[]>([]);
  const [currentUser, setCurrentUser] = useState<User | null>(null);
  const [content, setContent] = useState("");

  useEffect(() => {
    if (!id) {
      return;
    }
    fetchComments(id).then(setComments);
    fetchCurrentUser().then(setCurrentUser);
  }, [id]);

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    if (!id || !currentUser || content.trim() === "") {
      return;
    }
    const comment = await createComment(id, content, currentUser.id);
    setComments((previous) => [...previous, comment]);
    setContent("");
  }

  if (loading) {
    return <p>Carregando...</p>;
  }

  if (error || !task) {
    return <p>Task não encontrada.</p>;
  }

  return (
    <div className="task-detail">
      <Link to={`/projects/${task.projectId}`} className="back-link">
        ← Voltar
      </Link>
      <span className="task-key">{task.key}</span>
      <h2>{task.name}</h2>
      <p>Responsável: {task.assignee.name}</p>
      <p>
        <span className="status-tag">{STATUS_LABELS[task.status]}</span>{" "}
        <span className={`priority-tag priority-${task.priority.toLowerCase()}`}>
          {task.priority}
        </span>
      </p>
      {task.labels.length > 0 && <p>Labels: {task.labels.join(", ")}</p>}

      <h3>Comentários</h3>
      <ul className="comment-list">
        {comments.map((comment) => (
          <li key={comment.id} className="comment">
            <strong>{comment.author.name}</strong>
            <p>{comment.content}</p>
          </li>
        ))}
      </ul>

      <form onSubmit={handleSubmit} className="comment-form">
        <textarea
          value={content}
          onChange={(event) => setContent(event.target.value)}
          placeholder="Adicionar comentário..."
        />
        <button type="submit">Comentar</button>
      </form>
    </div>
  );
}

export default TaskDetailPage;
