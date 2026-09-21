import ProjectCard from "../components/ProjectCard";
import { useProjects } from "../hooks/useProjects";

function ProjectsPage() {
  const { projects, loading, error } = useProjects();

  if (loading) {
    return <p>Carregando...</p>;
  }

  if (error) {
    return <p>Erro ao carregar projetos: {error}</p>;
  }

  return (
    <div className="project-list">
      {projects.map((project) => (
        <ProjectCard key={project.id} project={project} />
      ))}
    </div>
  );
}

export default ProjectsPage;
