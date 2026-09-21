import type { User } from "../types/User";
import { apiFetch } from "./api";

export async function fetchCurrentUser(): Promise<User> {
  const response = await apiFetch("/users/me");
  if (!response.ok) {
    throw new Error(`Failed to fetch current user: ${response.status}`);
  }
  return response.json();
}
