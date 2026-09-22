import { Link } from "react-router-dom";
import type { Project } from "../types/Project";

interface ProjectCardProps {
  project: Project;
}

function ProjectCard({ project }: ProjectCardProps) {
  return (
    <Link to={`/projects/${project.id}`} className="project-card">
      <span className="task-key">{project.key}</span>
      <h3>{project.name}</h3>
      <p>{project.description}</p>
    </Link>
  );
}

export default ProjectCard;
