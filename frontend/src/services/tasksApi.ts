import type { Task } from "../types/Task";
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
