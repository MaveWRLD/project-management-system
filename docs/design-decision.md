
# Design Decisions

This document explains the architectural choices, trade‑offs, and coding conventions used in the Java **Project Management System**. It is intended for contributors and maintainers to understand *why* the system is structured the way it is and how to evolve it safely.

---

## 1) Architecture & Layering

### 1.1 Packages & Responsibilities
- **`models`**: Core domain entities and value objects
    - `Project` (abstract), `SoftwareProject`, `HardwareProject`
    - `Task`, `StatusReport`, `User` (abstract), `AdminUser`, `RegularUser`
- **`services`**: Application logic (use‑cases)
    - `ProjectService`, `TaskService`, `ReportService`, `UserService`
- **`interfaces`**: Cross‑cutting contracts
    - `Completable` for completion behavior
- **`utils`**: Helpers and fundamental utilities
    - `Status` (enum), `ValidationUtils`, `IDGenerator`
- **`utils.exceptions`**: Domain-specific errors
    - `InvalidInputException`, `TaskNotFoundException`, `ProjectNotFoundException`,
      `EmptyProjectException`, `ProjectNotCreatedException`

**Rationale**
- Clear separation of concerns keeps domain modeling (`models`) distinct from orchestration (`services`).
- `interfaces` isolates reusable contracts and prevents deep inheritance chains.
- `utils` centralizes shared primitives (ID creation, validation, enums).
- Dedicated `exceptions` improve readability and enable precise error flows.

**Trade‑off**
- A slightly larger number of packages increases navigation overhead but pays off with maintainability as the codebase grows.

---

## 2) Domain Modeling

### 2.1 Project Hierarchy
- `Project` holds *common project state* (id, name, budget, type, teamSize, tasks[]).
- Concrete types add *specialized fields*:
    - `SoftwareProject`: `technology`, `domain`, `versioning`
    - `HardwareProject`: `component`, `weight`

**Rationale**
- Using inheritance captures shared semantics and avoids duplication.
- Constructor of `Project` handles **ID assignment** (`'P'` prefix) and **registry growth**.

**Trade‑off**
- Inheritance can make cross‑type logic trickier. Composition is an alternative for future refactors if types proliferate.

### 2.2 Task Model
- `Task` implements `interfaces.Completable` and stores `taskID`, `name`, `status`.
- `isCompleted()` is a simple predicate on `Status`.

**Rationale**
- Interface keeps the completion contract flexible (other completable entities can be added later).
- `Status` enum (`PENDING`, `IN_PROGRESS`, `COMPLETED`) standardizes state.

---

## 3) Services & Use‑Cases

### 3.1 ProjectService
- Manages in‑memory array of `Project` instances.
- Provides **filters** by type and budget, plus **lookup** by ID.
- Resizes arrays when full (manual doubling strategy).

**Rationale**
- Simple CRUD and queries without persistence.
- Manual resizing avoids early dependency on collections or DB.

**Trade‑off**
- Arrays require manual null management and resizing; `List<Project>` would be cleaner and safer.
- Migration path: replace arrays with `ArrayList`, keep public API stable.

### 3.2 TaskService
- Adds tasks to projects, updates status, finds task indexes.
- Validates existence via `ProjectService` and throws/catches domain exceptions.

**Rationale**
- Centralizes **task orchestration** and keeps `Project` entity lean.
- Explicit exceptions improve error visibility at the callsite.

**Trade‑off**
- Multiple try/catch blocks can grow noisy. Consider a functional approach (return types with result/error) or a central error handler.

### 3.3 ReportService
- Computes `totalTask`, `completedTasks`, `completionPercentage`, and average completion.
- Produces `StatusReport[]` for easy consumption.

**Rationale**
- Reporting is a separate concern; derived metrics should live in a service layer.
- Keeps domain models free from heavy calculation logic.

**Trade‑off**
- Current implementation iterates arrays multiple times; for large datasets, consider caching or streaming.

### 3.4 UserService
- Provides `switchUser(currentUser)` toggling between `AdminUser` and `RegularUser`.

**Rationale**
- A simple way to test role‑based flows in a console application.

**Trade‑off**
- Static singletons limit extensibility (e.g., multiple sessions).
- Migration path: inject a `UserContext` that maintains the active user.

---

## 4) ID Generation Strategy

### 4.1 Prefix‑Based IDs
- `Project` IDs use `'P'` prefix; `Task` IDs use `'T'`; `User` uses `'U'`.
- `IDGenerator` produces string IDs (e.g., `P001`, `T002`).

**Rationale**
- Human‑readable IDs simplify console workflows and debugging.
- Prefixes encode type semantics without extra lookups.

**Trade‑off**
- No global uniqueness across runs; persistence requires a different strategy (UUIDs, DB sequences).

---

## 5) Validation & UX

### 5.1 ValidationUtils
- Centralized static methods for **int/string/type/status/ID** validation.
- Uses `InvalidInputException` for domain errors; catches `NumberFormatException` for parsing.

**Rationale**
- Single responsibility reduces duplication and keeps menus/controllers thin.
- Encourages consistent UX messages.

**Trade‑off**
- Static state (`Scanner`) can complicate testing; inject input streams in future to improve testability.

---

## 6) Exceptions & Error Handling

### 6.1 Custom Exceptions
- **`InvalidInputException`** — invalid user input (IDs, text, ranges)
- **`TaskNotFoundException`** — task lookups and updates that fail
- **`ProjectNotFoundException`** — missing project by ID
- **`ProjectNotCreatedException`** — no projects created when requested
- **`EmptyProjectException`** — tasks array is null or empty

**Rationale**
- Domain exceptions are more expressive than generic runtime errors.
- Allows callers to *recover gracefully* and show friendly messages.

**Relationships**
- `ProjectService` **throws** `ProjectNotCreatedException`, `ProjectNotFoundException`
- `TaskService` **throws** `EmptyProjectException`, `TaskNotFoundException`; **catches** `ProjectNotFoundException`
- `ReportService` **throws/catches** `EmptyProjectException`
- `AdminUser` **throws** `TaskNotFoundException`; **catches** `ProjectNotFoundException`, `EmptyProjectException`
- `ValidationUtils` **throws/catches** `InvalidInputException`

---

## 7) Data Structures & Complexity

### 7.1 Arrays + Doubling
- Arrays are used for `Project[]` and `Task[]` and **double in size** when full.

**Rationale**
- Minimal dependencies; easy to reason about memory growth.

**Complexity**
- Add operations: *amortized* O(1), occasional O(n) on resize
- Lookups by ID: O(n) across array
- Filtering: O(n)

**Trade‑off**
- Manual null checks and index management.
- Migration path: `List<>` collections → cleaner semantics, fewer foot‑guns.

---

## 8) UML & Documentation Choices

- **Clean class diagrams** using PlantUML reflect the codebase at a high level:
    - Inheritance, interfaces, composition, dependencies
    - Exception dependencies annotated with `<<throws>>` / `<<catches>>`
- **docs/** contains `.puml` sources and pre‑rendered images with a README explaining rendering via PlantUML + Graphviz.

**Rationale**
- Diagrams accelerate onboarding and code reviews.
- Keeping `.puml` in source control ensures diagrams evolve with code.

---

## 9) Testing Strategy (Roadmap)

- **Unit tests** for:
    - `ProjectService.filterProjectById`, `filterProject(type/budget)`
    - `TaskService.getTaskIndex`, `getProjectTasks`, `updateTaskStatus`
    - `ReportService.completionPercentage`, `completionAverage`
    - `ValidationUtils.isValidId`, type/status parsing
- **Error path tests** for each custom exception.
- **Property‑based tests** (optional) for ID generation and resizing logic.

---

## 10) Future Enhancements

1. **Collections**: Replace arrays with `List<>` or `Map<String, Task>` for task lookup O(1).
2. **Persistence**: Introduce a repository layer (JDBC/JPA) or file‑based storage.
3. **Authentication**: Expand `User` with credentials and RBAC; add `UserRepository`.
4. **ConsoleMenu → CLI/GUI**: Move to a cleaner CLI framework or basic Swing/JavaFX UI.
5. **Logging**: Use `java.util.logging` or SLF4J for structured logs instead of `System.out`.
6. **IDGenerator**: Switch to UUIDs or DB sequences for global uniqueness.
7. **Validation**: Make `ValidationUtils` instance‑based for better testability.

---

## 11) Known Limitations

- **Manual array management** (null checks, index shifting) is error‑prone.
- **Static singletons** in `UserService` restrict multi‑user sessions.
- **StatusReport** currently has a known typo (`completionPecentage`); prefer `completionPercentage` for consistency.

---

## 12) Coding Conventions

- **Naming**: `camelCase` for fields/methods; `PascalCase` for classes.
- **Visibility**: Favor `private` fields; expose data via getters/setters where needed.
- **Exceptions**: Use checked exceptions for expected recoverable conditions.
- **Immutability**: Consider immutability for value objects (future refactor).
- **Comments**: Prefer self-explanatory code and Javadoc on public APIs.

---

