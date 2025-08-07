### Pokemon Manager

#### This is a project for the educational portal, Streetofcode.sk. The project focuses on CRUD operations and connecting Java with a MySQL database using JDBC.

It's a simple Pokémon manager where you can create, delete, and update Pokémon or trainers. There are a few restrictions:

* You can only create a Pokémon with a name that genuinely exists (based on the first generation of Pokémon).

* Each Pokémon must have a unique identifier so we can distinguish between two Pikachus.

* Each trainer can have any number of Pokémon, but a Pokémon can only belong to one trainer.

* Pokémon can be deleted or caught for a specific trainer, but only if they are "wild" (meaning they don't have a trainer).

#### How to run
* under /src/main/resources is file init.sql, you need to run this in MySQL Workbench to create database
* under /src/main/resources create file application.properties and write inside
    ```
    db.name=pokemon
    db.user="your user name to MySQL Workbench"
    db.password="your password to MySQL Workbench"
  ```
* now you can run main method
