# Bookmaker Online

To create DB connection and fill DB with data:
1. Change database access properties for your database server in src/main/resources/database.properties file.
2. Create schema named "bookmaker" using info/create_scrypt.sql.
3. Import table with data in schema named "bookmaker" with help of info/insert_scrypt.sql file.