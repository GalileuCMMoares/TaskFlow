import type { Priority, Status, Task } from "../types/Task";
import { apiFetch } from "./api";

interface Page<T> {
  content: T[];
}

export async function fetchTasks(): Promise<Task[]> {
  const response = await apiFetch("/tasks?size=100");
  if (!response.ok) {
    throw new Error(`Failed to fetch tasks: ${response.status}`);
  }
  const page: Page<Task> = await response.json();
  return page.content;
}

export async function fetchTaskById(id: string): Promise<Task> {
  const response = await apiFetch(`/tasks/${id}`);
  if (!response.ok) {
    throw new Error(`Failed to fetch task ${id}: ${response.status}`);
  }
  return response.json();
}

export async function createTask(
  name: string,
  assigneeId: number,
  priority: Priority,
  projectId: number,
): Promise<Task> {
  const response = await apiFetch("/tasks", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      name,
      assigneeId,
      status: "PENDING",
      priority,
      projectId,
      labels: [],
    }),
  });
  if (!response.ok) {
    throw new Error(`Failed to create task: ${response.status}`);
  }
  return response.json();
}

export async function updateTaskStatus(task: Task, status: Status): Promise<Task> {
  const response = await apiFetch(`/tasks/${task.id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      name: task.name,
      assigneeId: task.assignee.id,
      status,
      priority: task.priority,
      projectId: task.projectId,
      labels: task.labels,
    }),
  });
  if (!response.ok) {
    throw new Error(`Failed to update task ${task.id}: ${response.status}`);
  }
  return response.json();
}
