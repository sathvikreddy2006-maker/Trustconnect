import { amount, date } from "../format.js";

export function PageHeading({ eyebrow, title, description, action }) {
  return (
    <div className="page-heading">
      <div>
        <p className="eyebrow">{eyebrow}</p>
        <h1>{title}</h1>
        {description && <p className="page-description">{description}</p>}
      </div>
      {action}
    </div>
  );
}

export function Metric({ label, value, detail, tone = "green" }) {
  return (
    <article className={`metric metric-${tone}`}>
      <p>{label}</p>
      <strong>{value}</strong>
      {detail && <span>{detail}</span>}
    </article>
  );
}

export function Amount({ value }) {
  return <span className="amount-value">{amount(value)}</span>;
}

export function DateValue({ value }) {
  return <time dateTime={value || undefined}>{date(value)}</time>;
}

export function Status({ value }) {
  const normalized = String(value || "unknown").toLowerCase();
  return <span className={`status status-${normalized}`}>{normalized}</span>;
}

export function Feedback({ error, success }) {
  if (error) return <p className="feedback feedback-error" role="alert">{error}</p>;
  if (success) return <p className="feedback feedback-success" role="status">{success}</p>;
  return null;
}

export function LoadState({ loading, error, empty, isEmpty = false, children }) {
  if (loading) return <div className="table-state">Loading records...</div>;
  if (error) return <div className="table-state state-error" role="alert">{error}</div>;
  if (isEmpty) return <div className="table-state">{empty}</div>;
  return children;
}