# encargo-3

## Descripción
Proyecto desarrollado con Spring Boot utilizando integración y entrega continua mediante GitHub Actions.

## Tecnologías utilizadas

- Java
- Spring Boot
- Maven
- Docker
- Docker Compose
- GitHub Actions
- Snyk
- Dependabot

## Funcionalidades del pipeline

El pipeline realiza automáticamente:

- Compilación del proyecto
- Escaneo de seguridad
- Construcción del proyecto
- Automatización CI/CD

## Seguridad

Se implementó Snyk para el análisis de vulnerabilidades y Dependabot para la revisión automática de dependencias.

## Orquestación

Se utilizó Docker Compose para la orquestación de contenedores.

El pipeline realiza automáticamente:

- Compilación del proyecto.
- Construcción de la imagen Docker.
- Escaneo de seguridad con Snyk.
- Publicación de la imagen en Docker Hub.
- Automatización del proceso de integración continua (CI/CD).

---

## Seguridad

Se implementó:

- Snyk para el análisis de vulnerabilidades.
- Dependabot para la revisión automática de dependencias.

---

## Despliegue

La aplicación fue desplegada en una instancia **Amazon EC2** utilizando **Docker Compose**.

Los contenedores ejecutados son:

- Aplicación Spring Boot.
- Base de datos PostgreSQL.

---

## Monitoreo

Se utilizó **Amazon CloudWatch** para monitorear el estado de la infraestructura.

Se creó un Dashboard con métricas como:

- CPU Utilization.
- Network In.
- Network Out.
- Status Check.

## Trazabilidad

GitHub Actions permite visualizar cada etapa del pipeline y mantener trazabilidad del proceso de integración continua.

## pipeline

El pipeline permite tomar decisiones objetivas en cada etapa:

Estabilidad: si las pruebas unitarias fallan → el merge se bloquea.

Calidad: si SonarQube detecta vulnerabilidades críticas → se corrige antes de integrar.

Cumplimiento interno: si los scripts de auditoría detectan violaciones → se rechaza el cambio.

Gobernanza: branch protection asegura que todo cambio pase por revisión y validación automática.
