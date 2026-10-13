import { useEffect, useState } from "react";
import { api, jsonBody } from "../api.js";
import { Feedback } from "../components/Primitives.jsx";
import { useResource } from "../hooks/useResource.js";

const initialValues = {
  fullName: "",
  email: "",
  password: "",
  trustId: "",
  trustName: "",
  registrationNumber: "",
  address: "",
  phone: "",
  trustEmail: ""
};

export default function AuthPage({ onLogin }) {
  const isRegisterRoute = window.location.pathname === "/register";
  const [mode, setMode] = useState("join");
  const [values, setValues] = useState(initialValues);
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState("");
  const trustsResource = useResource(() => api("trusts"), []);

  useEffect(() => {
    document.title = isRegisterRoute ? "Create account | TrustConnect" : "TrustConnect";
  }, [isRegisterRoute]);

  function change(field) {
    return (event) => setValues((current) => ({ ...current, [field]: event.target.value }));
  }

  async function submit(event) {
    event.preventDefault();
    setBusy(true);
    setError("");
    try {
      if (!isRegisterRoute) {
        const user = await api("auth/login", {
          method: "POST",
          body: jsonBody({ email: values.email, password: values.password })
        });
        onLogin(user);
        return;
      }

      if (mode === "trust") {
        await api("auth/register-trust", {
          method: "POST",
          body: jsonBody({
            fullName: values.fullName,
            email: values.email,
            password: values.password,
            trustName: values.trustName,
            registrationNumber: values.registrationNumber,
            address: values.address,
            phone: values.phone,
            trustEmail: values.trustEmail.trim() || null
          })
        });
      } else {
        await api(`members/trust/${encodeURIComponent(values.trustId)}`, {
          method: "POST",
          body: jsonBody({ fullName: values.fullName, email: values.email, password: values.password })
        });
      }

      window.location.assign("/");
    } catch (requestError) {
      setError(requestError.message || "Unable to complete this request.");
    } finally {
      setBusy(false);
    }
  }

  const isTrustForm = isRegisterRoute && mode === "trust";
  const trustLoadError = trustsResource.error;

  return (
    <main className="auth-layout">
      <section className="auth-story">
        <a className="brand auth-brand" href="/" aria-label="TrustConnect home">
          <span className="brand-mark">TC</span><span>TrustConnect</span>
        </a>
        <div className="story-copy">
          <p className="eyebrow">COMMUNITY, WELL ACCOUNTED FOR</p>
          <h1>Good work deserves good stewardship.</h1>
          <p>Bring your people, giving, and reporting together in one clear place.</p>
        </div>
        <p className="story-foot">A clearer way to care for your community.</p>
      </section>
      <section className="auth-panel">
        <div className="auth-card">
          <p className="eyebrow">{isRegisterRoute ? "GET STARTED" : "WELCOME BACK"}</p>
          <h2>{isRegisterRoute ? "Create your account" : "Sign in to your trust"}</h2>
          <p className="auth-intro">
            {isRegisterRoute ? "Choose how you would like to join TrustConnect." : "Manage your community with clarity and confidence."}
          </p>

          {isRegisterRoute && (
            <div className="segmented" role="group" aria-label="Registration type">
              <button className={mode === "join" ? "selected" : ""} onClick={() => setMode("join")} type="button">Join a trust</button>
              <button className={mode === "trust" ? "selected" : ""} onClick={() => setMode("trust")} type="button">Register a trust</button>
            </div>
          )}

          <form className="auth-form" onSubmit={submit}>
            {isRegisterRoute && (
              <label className="field field-wide">
                <span>Full name</span>
                <input autoComplete="name" onChange={change("fullName")} placeholder="Your full name" required value={values.fullName} />
              </label>
            )}
            <div className="field-row">
              <label className="field">
                <span>Email address</span>
                <input autoComplete="email" onChange={change("email")} placeholder="you@example.com" required type="email" value={values.email} />
              </label>
              <label className="field">
                <span>Password</span>
                <input autoComplete={isRegisterRoute ? "new-password" : "current-password"} minLength={6} onChange={change("password")} placeholder={isRegisterRoute ? "At least 6 characters" : "Enter your password"} required type="password" value={values.password} />
              </label>
            </div>

            {isRegisterRoute && !isTrustForm && (
              <label className="field field-wide">
                <span>Your trust</span>
                <select onChange={change("trustId")} required value={values.trustId}>
                  <option value="">Select your trust</option>
                  {(trustsResource.data || []).map((trust) => (
                    <option key={trust.id} value={trust.id}>{trust.trustName} · #{trust.id}</option>
                  ))}
                </select>
                {trustLoadError && <small className="field-error">{trustLoadError}</small>}
              </label>
            )}

            {isTrustForm && (
              <>
                <label className="field field-wide"><span>Trust name</span><input onChange={change("trustName")} placeholder="Organization or trust name" required value={values.trustName} /></label>
                <div className="field-row">
                  <label className="field"><span>Registration number <small>Optional</small></span><input onChange={change("registrationNumber")} placeholder="Official registration ID" value={values.registrationNumber} /></label>
                  <label className="field"><span>Trust phone <small>Optional</small></span><input onChange={change("phone")} placeholder="Contact phone" value={values.phone} /></label>
                </div>
                <label className="field field-wide"><span>Trust email <small>Optional</small></span><input onChange={change("trustEmail")} placeholder="Contact email for the trust" type="email" value={values.trustEmail} /></label>
                <label className="field field-wide"><span>Address <small>Optional</small></span><input onChange={change("address")} placeholder="Trust address" value={values.address} /></label>
              </>
            )}

            <Feedback error={error} />
            <button className="button button-primary auth-submit" disabled={busy || (isRegisterRoute && !isTrustForm && trustsResource.loading)} type="submit">
              {busy ? "Please wait..." : isRegisterRoute ? (isTrustForm ? "Register trust" : "Create member account") : "Sign in"}
              <span aria-hidden="true">→</span>
            </button>
          </form>
          <p className="auth-switch">
            {isRegisterRoute ? "Already have an account?" : "New to TrustConnect?"}{" "}
            <a href={isRegisterRoute ? "/" : "/register"}>{isRegisterRoute ? "Sign in" : "Create an account"}</a>
          </p>
        </div>
      </section>
    </main>
  );
}