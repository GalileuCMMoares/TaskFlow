import { useEffect, useState } from "react";
import type { Dispatch, SetStateAction } from "react";
import type { Task } from "../types/Task";
import { fetchTasksByProject } from "../services/tasksApi";

interface UseProjectTasksResult {
  tasks: Task[];
  setTasks: Dispatch<SetStateAction<Task[]>>;
  loading: boolean;
  error: string | null;
}

export function useProjectTasks(projectId: string): UseProjectTasksResult {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    setLoading(true);
    fetchTasksByProject(projectId)
      .then(setTasks)
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, [projectId]);

  return { tasks, setTasks, loading, error };
}
