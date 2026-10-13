import { useState } from "react";
import AppShell from "./components/AppShell.jsx";
import { api } from "./api.js";
import AuthPage from "./pages/AuthPage.jsx";
import DashboardPage from "./pages/DashboardPage.jsx";
import DonationsPage from "./pages/DonationsPage.jsx";
import DonorsPage from "./pages/DonorsPage.jsx";
import ExpensesPage from "./pages/ExpensesPage.jsx";
import MembersPage from "./pages/MembersPage.jsx";
import ReportsPage from "./pages/ReportsPage.jsx";

const pageByPath = {
  "/dashboard": DashboardPage,
  "/donors": DonorsPage,
  "/donations": DonationsPage,
  "/expenses": ExpensesPage,
  "/members": MembersPage,
  "/reports": ReportsPage
};

function readStoredUser() {
  try {
    return JSON.parse(window.localStorage.getItem("user") || "null");
  } catch {
    return null;
  }
}

export default function App() {
  const [user, setUser] = useState(readStoredUser);
  const path = window.location.pathname;
  const isAuthPage = path === "/" || path === "/register";
  const Page = pageByPath[path] || DashboardPage;

  function handleLogin(nextUser) {
    window.localStorage.setItem("user", JSON.stringify(nextUser));
    setUser(nextUser);
    window.location.assign("/dashboard");
  }

  async function handleLogout() {
    try {
      await api("auth/logout", { method: "POST" });
    } finally {
      window.localStorage.removeItem("user");
      setUser(null);
      window.location.assign("/");
    }
  }

  if (isAuthPage || !user) return <AuthPage onLogin={handleLogin} />;
  if (path === "/members" && user.role !== "ADMIN") window.location.replace("/dashboard");

  return (
    <AppShell onLogout={handleLogout} user={user}>
      <Page user={user} />
    </AppShell>
  );
}