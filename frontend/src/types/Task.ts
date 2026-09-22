import type { User } from "./User";

export type Status = "PENDING" | "IN_PROGRESS" | "DONE";
export type Priority = "LOW" | "MEDIUM" | "HIGH" | "URGENT";

export const STATUS_LABELS: Record<Status, string> = {
  PENDING: "Pendente",
  IN_PROGRESS: "Em andamento",
  DONE: "Concluída",
};

export interface Task {
  id: number;
  key: string;
  name: string;
  assignee: User;
  status: Status;
  priority: Priority;
  labels: string[];
  projectId: number;
}
