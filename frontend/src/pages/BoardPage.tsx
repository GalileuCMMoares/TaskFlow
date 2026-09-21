import { useTasks } from "../hooks/useTasks";
import TaskCard from "../components/TaskCard";
import type { Status } from "../types/Task";

const COLUMNS: { status: Status; label: string }[] = [
  { status: "PENDING", label: "Pendente" },
  { status: "IN_PROGRESS", label: "Em andamento" },
  { status: "DONE", label: "Concluída" },
];

function BoardPage() {
  const { tasks, loading, error } = useTasks();

  if (loading) {
    return <p>Carregando...</p>;
  }

  if (error) {
    return <p>Erro ao carregar tasks: {error}</p>;
  }

  return (
    <div className="board">
      {COLUMNS.map((column) => (
        <div key={column.status} className="board-column">
          <h3>{column.label}</h3>
          {tasks
            .filter((task) => task.status === column.status)
            .map((task) => (
              <TaskCard key={task.id} task={task} />
            ))}
        </div>
      ))}
    </div>
  );
}

export default BoardPage;
