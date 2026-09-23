import { Link, useParams } from "react-router-dom";
import { useProject } from "../hooks/useProject";
import ProjectBoard from "../components/ProjectBoard";

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
      <ProjectBoard projectId={id!} />
    </div>
  );
}

export default ProjectDetailPage;
