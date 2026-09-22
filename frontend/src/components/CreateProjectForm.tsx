import { useState } from "react";
import type { FormEvent } from "react";
import type { Project } from "../types/Project";
import { createProject } from "../services/projectsApi";

interface CreateProjectFormProps {
  onCreated: (project: Project) => void;
}

function CreateProjectForm({ onCreated }: CreateProjectFormProps) {
  const [key, setKey] = useState("");
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [error, setError] = useState<string | null>(null);

  async function handleSubmit(event: FormEvent) {
    event.preventDefault();
    try {
      const project = await createProject(key, name, description);
      onCreated(project);
      setKey("");
      setName("");
      setDescription("");
      setError(null);
    } catch {
      setError("Não foi possível criar o projeto.");
    }
  }

  return (
    <form onSubmit={handleSubmit} className="create-form">
      <input
        value={key}
        onChange={(event) => setKey(event.target.value)}
        placeholder="Chave (ex.: WEB)"
      />
      <input
        value={name}
        onChange={(event) => setName(event.target.value)}
        placeholder="Nome do projeto"
      />
      <input
        value={description}
        onChange={(event) => setDescription(event.target.value)}
        placeholder="Descrição"
      />
      {error && <p className="error">{error}</p>}
      <button type="submit">Criar projeto</button>
    </form>
  );
}

export default CreateProjectForm;
