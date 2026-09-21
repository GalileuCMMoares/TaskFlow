import type { Comment } from "../types/Comment";
import { apiFetch } from "./api";

export async function fetchComments(taskId: string): Promise<Comment[]> {
  const response = await apiFetch(`/tasks/${taskId}/comments`);
  if (!response.ok) {
    throw new Error(`Failed to fetch comments: ${response.status}`);
  }
  return response.json();
}

export async function createComment(taskId: string, content: string, authorId: number): Promise<Comment> {
  const response = await apiFetch(`/tasks/${taskId}/comments`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ content, authorId }),
  });
  if (!response.ok) {
    throw new Error(`Failed to create comment: ${response.status}`);
  }
  return response.json();
}
