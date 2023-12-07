# Use an official PostgreSQL runtime as a parent image
FROM postgres

# Set the environment variables
ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=admin
ENV POSTGRES_DB=postgres

# Copy the SQL script to initialize the database
COPY ./db/init.sql /docker-entrypoint-initdb.d/create_tables.sql

# Expose the PostgreSQL port
EXPOSE 5432