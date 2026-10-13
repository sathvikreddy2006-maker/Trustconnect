import { useEffect } from "react";
import { api } from "../api.js";
import { LoadState, PageHeading, Status } from "../components/Primitives.jsx";
import { useResource } from "../hooks/useResource.js";

export default function MembersPage({ user }) {
  const resource = useResource(() => api(`members/trust/${encodeURIComponent(user.trustId)}`), [user.trustId]);
  useEffect(() => { document.title = "Members | TrustConnect"; }, []);

  return (
    <>
      <PageHeading eyebrow="ACCESS" title="Trust members" description="People registered with this trust and their assigned roles." />
      <section className="panel table-panel">
        <div className="table-heading"><div><h2>Member directory</h2><span>{resource.data?.length ?? 0} records</span></div></div>
        <LoadState loading={resource.loading} error={resource.error} empty="No members are available for this trust." isEmpty={!resource.data?.length}>
          <div className="table-scroll"><table><thead><tr><th>Name</th><th>Email</th><th>Role</th></tr></thead>
            <tbody>{(resource.data || []).map((member) => <tr key={member.id}><td><strong>{member.fullName}</strong></td><td>{member.email}</td><td><Status value={member.role} /></td></tr>)}</tbody>
          </table></div>
        </LoadState>
      </section>
    </>
  );
}