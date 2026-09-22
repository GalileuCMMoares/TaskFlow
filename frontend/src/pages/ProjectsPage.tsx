import ProjectCard from "../components/ProjectCard";
import CreateProjectForm from "../components/CreateProjectForm";
import { useProjects } from "../hooks/useProjects";

function ProjectsPage() {
  const { projects, setProjects, loading, error } = useProjects();

  if (loading) {
    return <p>Carregando...</p>;
  }

  if (error) {
    return <p>Erro ao carregar projetos: {error}</p>;
  }

  return (
    <div className="project-list">
      <CreateProjectForm onCreated={(project) => setProjects((previous) => [...previous, project])} />
      {projects.map((project) => (
        <ProjectCard key={project.id} project={project} />
      ))}
    </div>
  );
}

export default ProjectsPage;
