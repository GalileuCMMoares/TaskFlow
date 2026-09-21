import type { Task } from "../types/Task";

interface TaskCardProps {
  task: Task;
}

function TaskCard({ task }: TaskCardProps) {
  return (
    <div className="task-card">
      <span className="task-key">{task.key}</span>
      <h4>{task.name}</h4>
      <p className="task-assignee">{task.assignee.name}</p>
      <span className={`priority-tag priority-${task.priority.toLowerCase()}`}>
        {task.priority}
      </span>
    </div>
  );
}

export default TaskCard;
