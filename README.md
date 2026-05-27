# encargo-2

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

## Trazabilidad

GitHub Actions permite visualizar cada etapa del pipeline y mantener trazabilidad del proceso de integración continua.
