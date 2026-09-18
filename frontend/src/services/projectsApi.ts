import type { Project } from "../types/Project";
import { apiFetch } from "./api";

interface Page<T> {
  content: T[];
}

export async function fetchProjects(): Promise<Project[]> {
  const response = await apiFetch("/projects");
  if (!response.ok) {
    throw new Error(`Failed to fetch projects: ${response.status}`);
  }
  const page: Page<Project> = await response.json();
  return page.content;
}

export async function fetchProjectById(id: string): Promise<Project> {
  const response = await apiFetch(`/projects/${id}`);
  if (!response.ok) {
    throw new Error(`Failed to fetch project ${id}: ${response.status}`);
  }
  return response.json();
}
