import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginWithGoogle } from "../services/auth";

function LoginPage() {
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  async function handleLogin() {
    try {
      await loginWithGoogle();
      navigate("/");
    } catch {
      setError("Não foi possível entrar com o Google.");
    }
  }

  return (
    <div className="login-form">
      <h2>Entrar</h2>
      {error && <p className="error">{error}</p>}
      <button type="button" onClick={handleLogin}>
        Entrar com Google
      </button>
    </div>
  );
}

export default LoginPage;
