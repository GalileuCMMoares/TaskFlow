import { Link, useParams } from "react-router-dom";
import { useProject } from "../hooks/useProject";

function ProjectDetailPage() {
  const { id } = useParams<{ id: string }>();
  const { project, loading, error } = useProject(id!);

  if (loading) {
    return <p>Carregando...</p>;
  }

  if (error || !project) {
    return <p>Projeto não encontrado.</p>;
  }

  return (
    <div className="project-detail">
      <Link to="/" className="back-link">
        ← Voltar
      </Link>
      <h2>{project.name}</h2>
      <p>{project.description}</p>
    </div>
  );
}

export default ProjectDetailPage;
