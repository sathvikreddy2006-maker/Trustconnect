import { useEffect, useState } from "react";
import { api } from "../api.js";
import { Amount, Feedback, Metric, PageHeading, Status } from "../components/Primitives.jsx";
import { useResource } from "../hooks/useResource.js";

const periods = ["WEEK", "MONTH", "YEAR"];

export default function ReportsPage({ user }) {
  const [period, setPeriod] = useState("YEAR");
  const resource = useResource(
    () => api(`reports/trust/${encodeURIComponent(user.trustId)}?period=${period}`),
    [user.trustId, period]
  );

  useEffect(() => { document.title = "Reports | TrustConnect"; }, []);

  return (
    <>
      <PageHeading eyebrow="FINANCIAL ACTIVITY" title="Reports" description="Donation and expense totals for the selected calendar period." />
      <div className="report-toolbar">
        <div className="segmented report-period" role="group" aria-label="Report period">
          {periods.map((value) => <button aria-pressed={period === value} className={period === value ? "selected" : ""} key={value} onClick={() => setPeriod(value)} type="button">{value[0] + value.slice(1).toLowerCase()}</button>)}
        </div>
        <span>Calendar {period.toLowerCase()} totals · amounts shown without currency</span>
      </div>
      {resource.error && <Feedback error={resource.error} />}
      {resource.loading ? <div className="table-state">Loading report...</div> : resource.data && (
        <>
          <section className="metric-grid report-metrics">
            <Metric label="Promised" value={<Amount value={resource.data.totalPromised} />} detail="Donation commitments" tone="mint" />
            <Metric label="Received" value={<Amount value={resource.data.totalReceived} />} detail="Donation receipts" tone="green" />
            <Metric label="Due" value={<Amount value={resource.data.totalDue} />} detail="Unreceived commitments" tone="amber" />
            <Metric label="Expenses" value={<Amount value={resource.data.totalExpenses} />} detail="Recorded spending" tone="coral" />
          </section>
          <section className="report-lower-grid">
            <article className="panel report-balance"><p className="eyebrow">CASH BASIS</p><h2>Period balance</h2><strong><Amount value={resource.data.balance} /></strong><p>Received donations minus recorded expenses during this period.</p></article>
            <article className="panel status-panel"><p className="eyebrow">DONATION RECORDS</p><h2>Status counts</h2><div className="status-count"><Status value="paid" /><strong>{resource.data.paidDonations}</strong></div><div className="status-count"><Status value="partial" /><strong>{resource.data.partialDonations}</strong></div><div className="status-count"><Status value="due" /><strong>{resource.data.dueDonations}</strong></div></article>
          </section>
        </>
      )}
    </>
  );
}