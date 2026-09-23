import { useState } from "react";
import type { FormEvent } from "react";
import { useUsers } from "../hooks/useUsers";
import { createTask } from "../services/tasksApi";
import type { Priority, Task } from "../types/Task";

interface CreateTaskFormProps {
  projectId: number;
  onCreated: (task: Task) => void;
}

const PRIORITIES: Priority[] = ["LOW", "MEDIUM", "HIGH", "URGENT"];

function CreateTaskForm({ projectId, onCreated }: CreateTaskFormProps) {
  const { users } = useUsers();
  const [name, setName] = useState("");
  const [assigneeId, setAssigneeId] = useState("");
  const [priority, setPriority] = useState<Priority>("MEDIUM");
  const [error, setError] = useState<string | null>(null);

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    if (!assigneeId) {
      setError("Escolha um responsável.");
      return;
    }
    try {
      const task = await createTask(name, Number(assigneeId), priority, projectId);
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
