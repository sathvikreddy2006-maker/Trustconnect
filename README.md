# TrustConnect

TrustConnect keeps the Spring Boot API under `backend/` and the editable React application under `frontend/`. Spring Boot serves the built frontend at `/`, and the frontend calls the API on the same origin.

## Run locally

Install Docker Desktop, copy `.env.example` to `.env`, replace both example passwords, then run:

```powershell
docker compose up --build
```

Open <http://localhost:8081>. MySQL data persists in the `mysql_data` volume. Stop the app with `docker compose down`; `docker compose down -v` also deletes the database.

## Deploy

The Docker build installs the locked frontend dependencies, builds React, packages those files with Spring Boot, and creates the runtime image. Deploy the image with port `8081` exposed. Configure these environment variables in the hosting platform:

- `SPRING_PROFILES_ACTIVE=prod`
- `PORT=8081`
- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, and `DB_PASSWORD` for a managed MySQL database

The production profile requires TLS for the MySQL connection. Keep database credentials in the host's secret manager, not in the repository. The default local Compose credentials are for development only.

## Frontend development

The JSX pages and shared components are under `frontend/src`. Start the backend from `backend/` with `mvn spring-boot:run`, then run `npm --prefix frontend ci` and `npm --prefix frontend run dev`; Vite proxies `/api` to Spring Boot. Run `npm --prefix frontend run build` before packaging locally with `mvn -f backend/pom.xml package`.

Java source, tests, and Maven configuration are in `backend/src` and `backend/pom.xml`. Frontend source, npm configuration, and page components are in `frontend/`.

Money is rendered without a currency symbol because the API does not define one. Dashboard and report totals come from backend response fields; the displayed balance is received donations minus expenses.