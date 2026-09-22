import type { User } from "../types/User";
import { apiFetch } from "./api";

interface Page<T> {
  content: T[];
}

export async function fetchCurrentUser(): Promise<User> {
  const response = await apiFetch("/users/me");
  if (!response.ok) {
    throw new Error(`Failed to fetch current user: ${response.status}`);
  }
  return response.json();
}

export async function fetchUsers(): Promise<User[]> {
  const response = await apiFetch("/users?size=100");
  if (!response.ok) {
    throw new Error(`Failed to fetch users: ${response.status}`);
  }
  const page: Page<User> = await response.json();
  return page.content;
}
