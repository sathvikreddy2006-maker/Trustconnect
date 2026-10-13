const navigation = [
  ["/dashboard", "Overview", "OV"],
  ["/donors", "Donors", "DO"],
  ["/donations", "Donations", "GI"],
  ["/expenses", "Expenses", "EX"],
  ["/reports", "Reports", "RP"]
];

export default function AppShell({ user, children, onLogout }) {
  const currentPath = window.location.pathname;
  const isAdmin = user.role === "ADMIN";
  const links = isAdmin
    ? [...navigation, ["/members", "Members", "TM"]]
    : navigation;

  return (
    <div className="app-frame">
      <aside className="sidebar">
        <a className="brand" href="/dashboard" aria-label="TrustConnect overview">
          <span className="brand-mark">TC</span>
          <span>TrustConnect</span>
        </a>
        <div className="trust-context">
          <span className="context-label">TRUST WORKSPACE</span>
          <strong>{user.trustName || "Your trust"}</strong>
        </div>
        <nav className="primary-nav" aria-label="Main navigation">
          {links.map(([href, label, mark]) => (
            <a
              className={currentPath === href ? "nav-link active" : "nav-link"}
              href={href}
              key={href}
            >
              <span className="nav-mark" aria-hidden="true">{mark}</span>
              <span>{label}</span>
            </a>
          ))}
        </nav>
        <div className="sidebar-bottom">
          <div className="user-block">
            <span className="avatar">{(user.fullName || "T").slice(0, 1).toUpperCase()}</span>
            <div>
              <strong>{user.fullName || user.email}</strong>
              <span>{user.role || "MEMBER"}</span>
            </div>
          </div>
          <button className="logout-button" onClick={onLogout} type="button">Sign out</button>
        </div>
      </aside>
      <main className="main-area">
        <header className="topbar">
          <span className="topbar-trust">{user.trustName || "TrustConnect"}</span>
          <span className="topbar-date">
            {new Intl.DateTimeFormat("en-IN", { dateStyle: "full" }).format(new Date())}
          </span>
        </header>
        <div className="page-content">{children}</div>
      </main>
    </div>
  );
}