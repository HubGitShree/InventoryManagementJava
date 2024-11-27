# Logging Functionality in Inventory Management System

This document illustrates how logging is implemented and used in the **Inventory Management System** project.

---

## Purpose of Logging
- Track the flow of the program.
- Debug issues effectively without cluttering the console.
- Monitor important events like:
    - Adding items to inventory.
    - Restock notifications.
    - Merging inventories.
    - Errors and warnings.

---

## Key Logging Features

1. **Logging Framework Used**
    - Java's `java.util.logging` package.
    - Configured to log both to the console and a file (`inventory.log`).

2. **Log Levels**
    - `INFO`: To log general information like system initialization, item addition.
    - `WARNING`: To log important events requiring attention, such as restocking notifications.
    - `SEVERE`: To log critical errors that need immediate action.

3. **File Logging**
    - Logs are saved to a file named `inventory.log` in the project directory.
    - Appends new logs without overwriting old ones.

4. **Formatter**
    - Uses `SimpleFormatter` for readable log entries.
    - Example log entry:
      ```
      Nov 26, 2024 12:00:00 PM inventorysystem.Main main
      INFO: Starting Inventory Management System
      ```