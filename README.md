# TrustConnect

TrustConnect keeps the Spring Boot API under `backend/` and the editable React application under `frontend/`. Spring Boot serves the built frontend at `/`, and the frontend calls the API on the same origin.

## Run locally

Install Docker Desktop, copy `.env.example` to `.env`, replace both example passwords, then run:

```powershell
docker compose up --build