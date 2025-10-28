workspace "StockVin" "WineInventory" {

    model {
        consumer = person "CONSUMER" "Purchases the distributed product."
        producer = person "PRODUCER" "Manages the wine production process."
        distributor = person "DISTRIBUTOR" "Places and tracks orders."
        payment = softwareSystem "PAYMENT SYSTEM" "Allows users to make payments within the platform."
        StockVin = softwareSystem "WINEINVENTORY SOFTWARE SYSTEM" "Comprehensive solution for managing the wine and pisco production process." {
            landing = container "LANDING PAGE" "First user interaction with the platform that introduces its benefits and features."
            single = container "SINGLE PAGE APPLICATION" "Main web application to manage inventories, orders, and user functions."
            database = container "DATABASE" "Stores and provides persistent data for the system."
            api = container "API APPLICATION" "Backend logic layer that connects the SPA with the database and external systems." {
                c1 = component "Inventory Controller" "Exposes REST endpoints for inventory operations (CRUD, movements, adjustments). Validates permissions and maps incoming requests to DTOs for the service layer."
                c2 = component "Inventory Management Service" "Contains business logic related to inventory changes: reservations, decrements after sales, physical adjustments, and transactional boundaries."
                c3 = component "Inventory Query Service" "Optimized for read-only operations: listings, searches, filtering, and aggregation (e.g., stock by location, movement history)."
                c4 = component "Inventory Repository" "Responsible for data access in the Inventory domain. Acts as a Repository/DAO layer between business logic and the DATABASE container. Provides transactional CRUD operations (getById, list with filters and pagination, save, update, delete) and domain-specific methods (decreaseStock, increaseStock, reserveStock, commitReservation, rollbackReservation). Ensures data consistency through ACID transactions or Saga patterns when needed. Manages concurrency through optimistic locking with versioning and optional pessimistic locking to prevent overselling. Recommends the use of composite indexes (productId + locationId + status) and aggregated queries for reporting. Supports integration with a read cache (e.g., Redis) with write-through or invalidation policies to improve performance. Logs all data changes for auditing (who, when, before/after) and emits domain events (InventoryReserved, StockDecreased) for eventual consistency with other bounded contexts. Implements error handling and resilience patterns (exponential retries, circuit breakers for transient failures). Provides structured logging and metrics for observability. Enforces security with parameterized queries/ORM usage, encryption of sensitive data, and access control at the repository level if required. Supports schema migrations with tools like Flyway or Liquibase. Exposes a clearly defined interface (e.g., IInventoryRepository) to facilitate unit and integration testing."
                c5 = component "Winemaking Controller" "Exposes endpoints for managing the winemaking process: batches, processes, measurements, and stages."
                c6 = component "Customer Controller" "Handles client-related operations: profiles, addresses, and purchase history."
                c7 = component "Winemaking Query Service" "Provides queries and materialized views for winemaking processes."
                c8 = component "Winemaking Management Service" "Implements business logic for winemaking: batch transformations, yield calculations, and process scheduling."
                c9 = component "Customer Management Service" "Manages customer creation and updates, data validation, and optional KYC verification."
                c10 = component "Customer Query Service" "Provides optimized search, segmentation, and pagination for customer data."
                c11 = component "Winemaking Repository" "Repository for persisting winemaking domain entities such as batches, parameters, and process records."
                c12 = component "Customer Repository" "Repository that stores customer data, addresses, preferences, and relationships."
                c13 = component "Order Controller" "Exposes REST endpoints for order creation and tracking. Orchestrates validation, inventory checks, and payment gateway integration."
                c14 = component "Order Management Service" "Implements business logic for order creation, stock validation, price calculation, and payment coordination."
                c15 = component "Order Query Service" "Provides search and listing functionalities for orders with filters and pagination."
                c16 = component "Order Repository" "Handles persistence of orders, payment statuses, and domain events related to the order lifecycle."
            }
        }

        // System Context
        consumer -> StockVin "Uses"
        producer -> StockVin "Manages credentials and profile"
        distributor -> StockVin "Places and tracks sales"
        StockVin -> payment "Retrieves user information to register"

        // Container relationships
        consumer -> landing "Uses"
        producer -> landing "Manages credentials and profile"
        distributor -> landing "Places and tracks sales"
        producer -> single "Manages services and sales"
        distributor -> single "Buys wines and continues the journey"
        landing -> single "Redirects user to the main application"
        single -> api "Makes API calls"
        api -> database "Reads and writes data"
        api -> payment "Retrieves user information to register"

        // Component relationships
        single -> c1 "Makes API calls"
        single -> c5 "Makes API calls"
        single -> c6 "Makes API calls"
        single -> c13 "Makes API calls"
        c1 -> c2 "Uses"
        c1 -> c3 "Uses"
        c2 -> c4 "Uses"
        c3 -> c4 "Uses"
        c4 -> database "Reads and writes"
        c5 -> c7 "Uses"
        c5 -> c8 "Uses"
        c6 -> c9 "Uses"
        c6 -> c10 "Uses"
        c7 -> c11 "Uses"
        c8 -> c11 "Uses"
        c9 -> c12 "Uses"
        c10 -> c12 "Uses"
        c11 -> database "Reads and writes"
        c12 -> database "Reads and writes"
        c13 -> c14 "Uses"
        c13 -> c15 "Uses"
        c14 -> c16 "Uses"
        c15 -> c16 "Uses"
        c16 -> database "Reads and writes"
    }

    views {
        systemContext StockVin {
            include *
        }
        container StockVin {
            include *
        }
        component api {
            include *
        }
        theme default
    }
}
