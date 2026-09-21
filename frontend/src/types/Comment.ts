import type { User } from "./User";

export interface Comment {
  id: number;
  content: string;
  author: User;
  createdAt: string;
}
