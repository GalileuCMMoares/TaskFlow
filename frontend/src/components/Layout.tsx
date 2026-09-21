import { Link, Outlet, useNavigate } from "react-router-dom";
import { logout } from "../services/auth";

function Layout() {
  const navigate = useNavigate();

  async function handleLogout() {
    await logout();
    navigate("/login");
  }

  return (
    <div>
      <nav className="navbar">
        <Link to="/">Projetos</Link>
        <Link to="/board">Board</Link>
        <button type="button" onClick={handleLogout}>
          Sair
        </button>
      </nav>
      <Outlet />
    </div>
  );
}

export default Layout;
