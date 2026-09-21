import { useEffect, useState } from "react";
import type { Task } from "../types/Task";
import { fetchTaskById } from "../services/tasksApi";

interface UseTaskResult {
  task: Task | null;
  loading: boolean;
  error: string | null;
}

export function useTask(id: string): UseTaskResult {
  const [task, setTask] = useState<Task | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    setLoading(true);
    fetchTaskById(id)
      .then(setTask)
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, [id]);

  return { task, loading, error };
}
