import { useState } from "react";
import type { FormEvent } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../services/auth";

function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  function handleSubmit(event: FormEvent) {
    event.preventDefault();
    const success = login(username, password);
    if (!success) {
      setError("Usuário e senha são obrigatórios.");
      return;
    }
    navigate("/");
  }

  return (
    <form className="login-form" onSubmit={handleSubmit}>
      <h2>Entrar</h2>
      <input
        type="text"
        placeholder="Usuário"
        value={username}
        onChange={(event) => setUsername(event.target.value)}
      />
      <input
        type="password"
        placeholder="Senha"
        value={password}
        onChange={(event) => setPassword(event.target.value)}
      />
      {error && <p className="error">{error}</p>}
      <button type="submit">Entrar</button>
    </form>
  );
}

export default LoginPage;
