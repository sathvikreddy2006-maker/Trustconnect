import { useEffect, useState } from "react";
import { api, jsonBody } from "../api.js";
import { Amount, DateValue, Feedback, LoadState, PageHeading } from "../components/Primitives.jsx";
import { useResource } from "../hooks/useResource.js";
import { sumAmounts, today } from "../format.js";

const blankExpense = { description: "", category: "", amount: "", expenseDate: today() };

export default function ExpensesPage({ user }) {
  const [expense, setExpense] = useState(blankExpense);
  const [showForm, setShowForm] = useState(false);
  const [refreshKey, setRefreshKey] = useState(0);
  const [feedback, setFeedback] = useState({ error: "", success: "" });
  const resource = useResource(() => api(`expenses/trust/${encodeURIComponent(user.trustId)}`), [user.trustId, refreshKey]);

  useEffect(() => { document.title = "Expenses | TrustConnect"; }, []);

  async function submit(event) {
    event.preventDefault();
    const numericAmount = Number(expense.amount);
    if (!Number.isFinite(numericAmount) || numericAmount <= 0) {
      setFeedback({ error: "Enter an expense amount greater than zero.", success: "" });
      return;
    }
    try {
      await api(`expenses/trust/${encodeURIComponent(user.trustId)}`, { method: "POST", body: jsonBody({ ...expense, amount: numericAmount }) });
      setExpense({ ...blankExpense, expenseDate: today() });
      setShowForm(false);
      setRefreshKey((value) => value + 1);
      setFeedback({ error: "", success: "Expense recorded." });
    } catch (error) {
      setFeedback({ error: error.message, success: "" });
    }
  }

  async function removeExpense(id) {
    if (!window.confirm("Delete this expense record?")) return;
    try {
      await api(`expenses/${id}`, { method: "DELETE" });
      setRefreshKey((value) => value + 1);
      setFeedback({ error: "", success: "Expense deleted." });
    } catch (error) {
      setFeedback({ error: error.message, success: "" });
    }
  }

  const total = sumAmounts((resource.data || []).map((item) => item.amount));

  return (
    <>
      <PageHeading eyebrow="SPENDING" title="Expenses" description="Recorded outflows associated with your trust." action={<button className="button button-primary" onClick={() => setShowForm((value) => !value)} type="button">{showForm ? "Close form" : "Record expense"}</button>} />
      <Feedback {...feedback} />
      <section className="compact-summary"><div><span>Expense records</span><strong>{resource.data?.length ?? 0}</strong></div><div><span>Total in listed records</span><strong><Amount value={total} /></strong></div></section>
      {showForm && (
        <form className="panel form-panel" onSubmit={submit}>
          <div className="form-heading"><h2>New expense</h2><span>Enter the amount as a positive number.</span></div>
          <div className="form-grid">
            <label className="field"><span>Description</span><input onChange={(event) => setExpense({ ...expense, description: event.target.value })} required value={expense.description} /></label>
            <label className="field"><span>Category</span><input onChange={(event) => setExpense({ ...expense, category: event.target.value })} required value={expense.category} /></label>
            <label className="field"><span>Amount</span><input min="0.01" onChange={(event) => setExpense({ ...expense, amount: event.target.value })} required step="0.01" type="number" value={expense.amount} /></label>
            <label className="field"><span>Expense date</span><input onChange={(event) => setExpense({ ...expense, expenseDate: event.target.value })} required type="date" value={expense.expenseDate} /></label>
          </div>
          <div className="form-actions"><button className="button button-primary" type="submit">Save expense</button></div>
        </form>
      )}
      <section className="panel table-panel">
        <div className="table-heading"><div><h2>Expense register</h2><span>Amounts shown as stored; currency is not specified by the API.</span></div></div>
        <LoadState loading={resource.loading} error={resource.error} empty="No expenses recorded for this trust yet." isEmpty={!resource.data?.length}>
          <div className="table-scroll"><table><thead><tr><th>Description</th><th>Category</th><th>Amount</th><th>Date</th><th></th></tr></thead>
            <tbody>{(resource.data || []).map((item) => <tr key={item.id}><td><strong>{item.description}</strong></td><td>{item.category || "-"}</td><td><Amount value={item.amount} /></td><td><DateValue value={item.expenseDate} /></td><td><button className="text-button danger-text" onClick={() => removeExpense(item.id)} type="button">Delete</button></td></tr>)}</tbody>
          </table></div>
        </LoadState>
      </section>
    </>
  );
}