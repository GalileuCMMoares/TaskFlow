const TOKEN_KEY = "taskflow_token";

export function login(username: string, password: string): boolean {
  if (username.trim() === "" || password.trim() === "") {
    return false;
  }
  localStorage.setItem(TOKEN_KEY, "mock-token");
  return true;
}

export function logout(): void {
  localStorage.removeItem(TOKEN_KEY);
}

export function isAuthenticated(): boolean {
  return localStorage.getItem(TOKEN_KEY) !== null;
}
