import { useNavigate } from "react-router-dom";
import ProjectCard from "../components/ProjectCard";
import { useProjects } from "../hooks/useProjects";
import { logout } from "../services/auth";

function ProjectsPage() {
  const { projects, loading, error } = useProjects();
  const navigate = useNavigate();

  async function handleLogout() {
    await logout();
    navigate("/login");
  }

  if (loading) {
    return <p>Carregando...</p>;
  }

  if (error) {
    return <p>Erro ao carregar projetos: {error}</p>;
  }

  return (
    <div className="project-list">
      <button type="button" onClick={handleLogout}>
        Sair
      </button>
      {projects.map((project) => (
        <ProjectCard key={project.id} project={project} />
      ))}
    </div>
  );
}

export default ProjectsPage;
