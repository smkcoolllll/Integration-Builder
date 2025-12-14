# CloudEagle AI-Generated Integration Builder

## Overview
Spring Boot application that dramatically simplifies integrations with SaaS applications by:
- Supporting multiple external APIs (Calendly, Dropbox, etc.)
- Dynamic/configurable endpoints without redeployment
- Generic API client that works with any REST API
- Persistent storage of configurations and fetched users
- Multi-environment support (sandbox & production)

## Key Features
✅ 10 REST API endpoints
✅ Generic API client (works with any external API)
✅ Dynamic configuration (database-driven)
✅ Multi-environment support
✅ Field mapping and transformation
✅ Comprehensive error handling
✅ Database persistence
✅ Full audit trail

## Quick Start
```bash
mvn clean install
mvn spring-boot:run
```

## API Endpoints
- POST /api/v1/integration/config - Create configuration
- GET /api/v1/integration/config/{appName}/{environment} - Get configuration
- POST /api/v1/integration/fetch/{appName}/{environment} - Fetch users
- GET /api/v1/integration/users/{appName}/{environment} - Get users
- And 6 more endpoints (see docs/API_ENDPOINTS.md)

## Documentation
See docs/ folder for:
- FIGMA_DESIGN.md - UI/UX design and workflows
- SAFEGUARDS.md - Security and best practices


## Technology Stack
- Java 17
- Spring Boot 3.1.5
- Spring Data JPA
- H2 Database (Development)
- PostgreSQL (Production Ready)
- Gson (JSON Processing)
- Lombok (Code Generation)

## Requirements Met
✅ Support fetching from multiple external systems
✅ Dynamic/configurable API endpoints
✅ Database storage for configurations
✅ Generic method for any external API
✅ Store fetched users in database
✅ Full Calendly implementation
✅ UI/UX Design (Figma)
✅ Security safeguards (8 implemented)
✅ AI model selection (Claude Sonnet 3.5)
