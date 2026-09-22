import { Link } from "react-router-dom";
import type { Task } from "../types/Task";

interface TaskCardProps {
  task: Task;
}

function TaskCard({ task }: TaskCardProps) {
  return (
    <Link
      to={`/tasks/${task.id}`}
      className="task-card"
      draggable
      onDragStart={(event) => event.dataTransfer.setData("text/plain", String(task.id))}
    >
      <span className="task-key">{task.key}</span>
      <h4>{task.name}</h4>
      <p className="task-assignee">{task.assignee.name}</p>
      <span className={`priority-tag priority-${task.priority.toLowerCase()}`}>
        {task.priority}
      </span>
    </Link>
  );
}

export default TaskCard;
