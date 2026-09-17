import { useEffect, useState } from "react";
import type { Project } from "../types/Project";
import { fetchProjectById } from "../services/projectsApi";

interface UseProjectResult {
  project: Project | null;
  loading: boolean;
  error: string | null;
}

export function useProject(id: string): UseProjectResult {
  const [project, setProject] = useState<Project | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    setLoading(true);
    fetchProjectById(id)
      .then(setProject)
      .catch((err) => setError(err.message))
      .finally(() => setLoading(false));
  }, [id]);

  return { project, loading, error };
}
