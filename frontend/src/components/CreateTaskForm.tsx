import { useState } from "react";
import type { FormEvent } from "react";
import { useProjects } from "../hooks/useProjects";
import { useUsers } from "../hooks/useUsers";
import { createTask } from "../services/tasksApi";
import type { Priority, Task } from "../types/Task";

interface CreateTaskFormProps {
  onCreated: (task: Task) => void;
}

const PRIORITIES: Priority[] = ["LOW", "MEDIUM", "HIGH", "URGENT"];

function CreateTaskForm({ onCreated }: CreateTaskFormProps) {
  const { projects } = useProjects();
  const { users } = useUsers();
  const [name, setName] = useState("");
  const [projectId, setProjectId] = useState("");
  const [assigneeId, setAssigneeId] = useState("");
  const [priority, setPriority] = useState<Priority>("MEDIUM");
  const [error, setError] = useState<string | null>(null);

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    if (!projectId || !assigneeId) {
      setError("Escolha um projeto e um responsável.");
      return;
    }
    try {
      const task = await createTask(name, Number(assigneeId), priority, Number(projectId));
      onCreated(task);
      setName("");
      setError(null);
    } catch {
      setError("Não foi possível criar a task.");
    }
  }

  return (
    <form onSubmit={handleSubmit} className="create-form">
      <input
        value={name}
        onChange={(event) => setName(event.target.value)}
        placeholder="Nome da task"
      />
      <select value={projectId} onChange={(event) => setProjectId(event.target.value)}>
        <option value="">Projeto...</option>
        {projects.map((project) => (
          <option key={project.id} value={project.id}>
            {project.key} — {project.name}
          </option>
        ))}
      </select>
      <select value={assigneeId} onChange={(event) => setAssigneeId(event.target.value)}>
        <option value="">Responsável...</option>
        {users.map((user) => (
          <option key={user.id} value={user.id}>
            {user.name}
          </option>
        ))}
      </select>
      <select value={priority} onChange={(event) => setPriority(event.target.value as Priority)}>
        {PRIORITIES.map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </select>
      {error && <p className="error">{error}</p>}
      <button type="submit">Criar task</button>
    </form>
  );
}

export default CreateTaskForm;
