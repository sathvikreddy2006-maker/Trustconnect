import { useEffect } from "react";
import { api } from "../api.js";
import { Amount, Metric, PageHeading } from "../components/Primitives.jsx";
import { useResource } from "../hooks/useResource.js";

export default function DashboardPage({ user }) {
  const { data, loading, error } = useResource(
    () => api(`dashboard/trust/${encodeURIComponent(user.trustId)}`),
    [user.trustId]
  );

  useEffect(() => { document.title = "Overview | TrustConnect"; }, []);

  const received = Number(data?.totalReceived ?? 0);
  const promised = Number(data?.totalPromised ?? 0);
  const progress = promised > 0 ? Math.min((received / promised) * 100, 100) : 0;

  return (
    <>
      <PageHeading eyebrow="TRUST OVERVIEW" title={`Good day, ${(user.fullName || "there").split(" ")[0]}.`} description="A current view of recorded giving and spending for your trust." />
      {error && <div className="feedback feedback-error" role="alert">{error}</div>}
      {loading ? <div className="table-state">Loading trust totals...</div> : data && (
        <>
          <section className="metric-grid" aria-label="Trust totals">
            <Metric label="Received" value={<Amount value={data.totalReceived} />} detail="Recorded donations received" tone="green" />
            <Metric label="Promised" value={<Amount value={data.totalPromised} />} detail="Total pledged donations" tone="mint" />
            <Metric label="Still due" value={<Amount value={data.totalDue} />} detail="Outstanding donation amounts" tone="amber" />
            <Metric label="Expenses" value={<Amount value={data.totalExpenses} />} detail="All recorded trust expenses" tone="coral" />
          </section>
          <section className="overview-grid">
            <article className="panel cash-panel">
              <div className="panel-heading">
                <div><p className="eyebrow">AVAILABLE FROM RECORDED CASH FLOW</p><h2>Operating balance</h2></div>
                <span className="balance-mark">=</span>
              </div>
              <strong className="balance-amount"><Amount value={data.balance} /></strong>
              <p className="formula">Received <Amount value={data.totalReceived} /> minus expenses <Amount value={data.totalExpenses} /></p>
              <div className="progress-label"><span>Received against promises</span><strong>{progress.toFixed(1)}%</strong></div>
              <div className="progress-track" role="progressbar" aria-valuenow={progress} aria-valuemin="0" aria-valuemax="100"><span style={{ width: `${progress}%` }} /></div>
              <p className="currency-note">Amounts are displayed as stored; the API does not specify a currency.</p>
            </article>
            <article className="panel next-panel">
              <p className="eyebrow">QUICK ACCESS</p>
              <h2>Keep the records current</h2>
              <div className="quick-links">
                <a href="/donations"><span className="quick-mark">GI</span><span><strong>Review donations</strong><small>Received, promised, and due</small></span><b>→</b></a>
                <a href="/expenses"><span className="quick-mark quick-mark-warm">EX</span><span><strong>Record an expense</strong><small>Keep spending visible</small></span><b>→</b></a>
                <a href="/reports"><span className="quick-mark quick-mark-blue">RP</span><span><strong>Open reports</strong><small>Choose a reporting period</small></span><b>→</b></a>
              </div>
            </article>
          </section>
        </>
      )}
    </>
  );
}