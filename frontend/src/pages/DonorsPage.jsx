import { useEffect, useState } from "react";
import { api, jsonBody } from "../api.js";
import { Feedback, LoadState, PageHeading } from "../components/Primitives.jsx";
import { useResource } from "../hooks/useResource.js";

const blankDonor = { name: "", email: "", phone: "", address: "" };

export default function DonorsPage({ user }) {
  const [donor, setDonor] = useState(blankDonor);
  const [showForm, setShowForm] = useState(false);
  const [feedback, setFeedback] = useState({ error: "", success: "" });
  const [refreshKey, setRefreshKey] = useState(0);
  const resource = useResource(() => api(`donors/trust/${encodeURIComponent(user.trustId)}`), [user.trustId, refreshKey]);

  useEffect(() => { document.title = "Donors | TrustConnect"; }, []);

  async function submit(event) {
    event.preventDefault();
    setFeedback({ error: "", success: "" });
    try {
      await api(`donors/trust/${encodeURIComponent(user.trustId)}`, { method: "POST", body: jsonBody(donor) });
      setDonor(blankDonor);
      setShowForm(false);
      setRefreshKey((value) => value + 1);
      setFeedback({ success: "Donor added.", error: "" });
    } catch (error) {
      setFeedback({ error: error.message, success: "" });
    }
  }

  return (
    <>
      <PageHeading eyebrow="PEOPLE" title="Donors" description="Contact records for people who support your trust." action={<button className="button button-primary" onClick={() => setShowForm((value) => !value)} type="button">{showForm ? "Close form" : "Add donor"}</button>} />
      <Feedback {...feedback} />
      {showForm && (
        <form className="panel form-panel" onSubmit={submit}>
          <div className="form-heading"><h2>New donor</h2><span>Contact details can be updated later.</span></div>
          <div className="form-grid">
            <label className="field"><span>Full name</span><input onChange={(event) => setDonor({ ...donor, name: event.target.value })} required value={donor.name} /></label>
            <label className="field"><span>Email</span><input onChange={(event) => setDonor({ ...donor, email: event.target.value })} type="email" value={donor.email} /></label>
            <label className="field"><span>Phone</span><input onChange={(event) => setDonor({ ...donor, phone: event.target.value })} value={donor.phone} /></label>
            <label className="field"><span>Address</span><input onChange={(event) => setDonor({ ...donor, address: event.target.value })} value={donor.address} /></label>
          </div>
          <div className="form-actions"><button className="button button-primary" type="submit">Save donor</button></div>
        </form>
      )}
      <section className="panel table-panel">
        <div className="table-heading"><div><h2>Donor directory</h2><span>{resource.data?.length ?? 0} records</span></div></div>
        <LoadState loading={resource.loading} error={resource.error} empty="No donors recorded for this trust yet." isEmpty={!resource.data?.length}>
          <div className="table-scroll"><table><thead><tr><th>Name</th><th>Email</th><th>Phone</th><th>Address</th></tr></thead>
            <tbody>{(resource.data || []).map((item) => <tr key={item.id}><td><strong>{item.name}</strong></td><td>{item.email || "-"}</td><td>{item.phone || "-"}</td><td>{item.address || "-"}</td></tr>)}</tbody>
          </table></div>
        </LoadState>
      </section>
    </>
  );
}