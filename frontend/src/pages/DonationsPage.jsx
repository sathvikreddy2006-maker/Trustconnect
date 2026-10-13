import { useEffect, useState } from "react";
import { api, jsonBody } from "../api.js";
import { Amount, DateValue, Feedback, LoadState, PageHeading, Status } from "../components/Primitives.jsx";
import { useResource } from "../hooks/useResource.js";
import { sumAmounts, today } from "../format.js";

const blankGift = { donorId: "", promisedAmount: "", receivedAmount: "0", donationDate: today(), dueDate: "" };

export default function DonationsPage({ user }) {
  const [gift, setGift] = useState(blankGift);
  const [showForm, setShowForm] = useState(false);
  const [refreshKey, setRefreshKey] = useState(0);
  const [feedback, setFeedback] = useState({ error: "", success: "" });
  const donorResource = useResource(() => api(`donors/trust/${encodeURIComponent(user.trustId)}`), [user.trustId]);
  const resource = useResource(async () => {
    const donors = await api(`donors/trust/${encodeURIComponent(user.trustId)}`);
    const donationLists = await Promise.all(donors.map((donor) => api(`donations/donor/${donor.id}`).catch(() => [])));
    return donationLists.flat();
  }, [user.trustId, refreshKey]);

  useEffect(() => { document.title = "Donations | TrustConnect"; }, []);

  async function submit(event) {
    event.preventDefault();
    setFeedback({ error: "", success: "" });
    const promisedAmount = Number(gift.promisedAmount);
    const receivedAmount = Number(gift.receivedAmount);
    if (!Number.isFinite(promisedAmount) || promisedAmount <= 0 || !Number.isFinite(receivedAmount) || receivedAmount < 0 || receivedAmount > promisedAmount) {
      setFeedback({ error: "Enter a positive promise and a received amount between zero and the promise.", success: "" });
      return;
    }
    try {
      await api(`donations/donor/${encodeURIComponent(gift.donorId)}`, {
        method: "POST",
        body: jsonBody({ promisedAmount, receivedAmount, donationDate: gift.donationDate, dueDate: gift.dueDate || null })
      });
      setGift({ ...blankGift, donationDate: today() });
      setShowForm(false);
      setRefreshKey((value) => value + 1);
      setFeedback({ error: "", success: "Donation recorded. Due and status were calculated by the server." });
    } catch (error) {
      setFeedback({ error: error.message, success: "" });
    }
  }

  async function updateFollowUp(item) {
    try {
      await api(`donations/${item.id}/follow-up?contacted=${!item.followUpContacted}`, { method: "PUT" });
      setRefreshKey((value) => value + 1);
    } catch (error) {
      setFeedback({ error: error.message, success: "" });
    }
  }

  const totalReceived = sumAmounts((resource.data || []).map((item) => item.receivedAmount));
  const totalDue = sumAmounts((resource.data || []).map((item) => item.dueAmount));

  return (
    <>
      <PageHeading eyebrow="GIVING" title="Donations" description="Promises, receipts, and outstanding amounts recorded against each donor." action={<button className="button button-primary" onClick={() => setShowForm((value) => !value)} type="button">{showForm ? "Close form" : "Record donation"}</button>} />
      <Feedback {...feedback} />
      <section className="compact-summary">
        <div><span>Records</span><strong>{resource.data?.length ?? 0}</strong></div>
        <div><span>Received in listed records</span><strong><Amount value={totalReceived} /></strong></div>
        <div><span>Due in listed records</span><strong><Amount value={totalDue} /></strong></div>
      </section>
      {showForm && (
        <form className="panel form-panel" onSubmit={submit}>
          <div className="form-heading"><h2>New donation</h2><span>Due amount and status are calculated by the backend.</span></div>
          <div className="form-grid">
            <label className="field"><span>Donor</span><select onChange={(event) => setGift({ ...gift, donorId: event.target.value })} required value={gift.donorId}><option value="">Choose a donor</option>{(donorResource.data || []).map((donor) => <option key={donor.id} value={donor.id}>{donor.name}</option>)}</select></label>
            <label className="field"><span>Promised amount</span><input min="0.01" onChange={(event) => setGift({ ...gift, promisedAmount: event.target.value })} required step="0.01" type="number" value={gift.promisedAmount} /></label>
            <label className="field"><span>Received amount</span><input min="0" onChange={(event) => setGift({ ...gift, receivedAmount: event.target.value })} required step="0.01" type="number" value={gift.receivedAmount} /></label>
            <label className="field"><span>Donation date</span><input onChange={(event) => setGift({ ...gift, donationDate: event.target.value })} required type="date" value={gift.donationDate} /></label>
            <label className="field"><span>Due date <small>Optional</small></span><input onChange={(event) => setGift({ ...gift, dueDate: event.target.value })} type="date" value={gift.dueDate} /></label>
          </div>
          {donorResource.error && <Feedback error={donorResource.error} />}
          <div className="form-actions"><button className="button button-primary" disabled={donorResource.loading} type="submit">Save donation</button></div>
        </form>
      )}
      <section className="panel table-panel">
        <div className="table-heading"><div><h2>Donation register</h2><span>Amounts shown as stored; currency is not specified by the API.</span></div></div>
        <LoadState loading={resource.loading} error={resource.error} empty="No donations recorded for this trust yet." isEmpty={!resource.data?.length}>
          <div className="table-scroll"><table><thead><tr><th>Donor</th><th>Promised</th><th>Received</th><th>Due</th><th>Status</th><th>Date</th><th>Follow-up</th></tr></thead>
            <tbody>{(resource.data || []).map((item) => <tr key={item.id}><td><strong>{item.donor?.name || "Donor"}</strong><small>{item.donor?.email || ""}</small></td><td><Amount value={item.promisedAmount} /></td><td><Amount value={item.receivedAmount} /></td><td><Amount value={item.dueAmount} /></td><td><Status value={item.status} /></td><td><DateValue value={item.donationDate} /></td><td>{Number(item.dueAmount || 0) > 0 ? <button className="text-button" onClick={() => updateFollowUp(item)} type="button">{item.followUpContacted ? "Contacted · undo" : "Mark contacted"}</button> : "-"}</td></tr>)}</tbody>
          </table></div>
        </LoadState>
      </section>
    </>
  );
}