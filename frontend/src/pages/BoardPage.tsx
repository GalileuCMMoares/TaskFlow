import { useState } from "react";
import type { DragEvent } from "react";
import { useTasks } from "../hooks/useTasks";
import { updateTaskStatus } from "../services/tasksApi";
import TaskCard from "../components/TaskCard";
import CreateTaskForm from "../components/CreateTaskForm";
import type { Status } from "../types/Task";
import { STATUS_LABELS } from "../types/Task";

const STATUSES: Status[] = ["PENDING", "IN_PROGRESS", "DONE"];

function BoardPage() {
  const { tasks, setTasks, loading, error } = useTasks();
  const [dragOverStatus, setDragOverStatus] = useState<Status | null>(null);

  async function handleDrop(event: DragEvent<HTMLDivElement>, status: Status) {
    event.preventDefault();
    setDragOverStatus(null);

    const taskId = Number(event.dataTransfer.getData("text/plain"));
    const task = tasks.find((candidate) => candidate.id === taskId);
    if (!task || task.status === status) {
      return;
    }

    const updated = await updateTaskStatus(task, status);
    setTasks((previous) => previous.map((candidate) => (candidate.id === updated.id ? updated : candidate)));
  }

  if (loading) {
    return <p>Carregando...</p>;
  }

  if (error) {
    return <p>Erro ao carregar tasks: {error}</p>;
  }

  return (
    <div>
      <CreateTaskForm onCreated={(task) => setTasks((previous) => [...previous, task])} />
      <div className="board">
        {STATUSES.map((status) => (
          <div
            key={status}
            className={`board-column ${dragOverStatus === status ? "drag-over" : ""}`}
            onDragOver={(event) => {
              event.preventDefault();
              setDragOverStatus(status);
            }}
            onDragLeave={() => setDragOverStatus(null)}
            onDrop={(event) => handleDrop(event, status)}
          >
            <h3>{STATUS_LABELS[status]}</h3>
            {tasks
              .filter((task) => task.status === status)
              .map((task) => (
                <TaskCard key={task.id} task={task} />
              ))}
          </div>
        ))}
      </div>
    </div>
  );
}

export default BoardPage;
