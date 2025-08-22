--liquibase formatted sql

-- changeset system:v0.0.0-create-registered-client-table
CREATE TABLE IF NOT EXISTS registered_client
(
    id                            CHAR(27) PRIMARY KEY NOT NULL,
    client_id                     VARCHAR(100)         NOT NULL,
    client_id_issued_at           TIMESTAMP,
    client_secret                 VARCHAR(200),
    client_secret_expires_at      TIMESTAMP,
    client_name                   VARCHAR(200)         NOT NULL,
    client_authentication_methods VARCHAR(1000)        NOT NULL,
    authorization_grant_types     VARCHAR(1000)        NOT NULL,
    redirect_uris                 VARCHAR(1000),
    scopes                        VARCHAR(1000)        NOT NULL,
    client_settings               VARCHAR(2000)        NOT NULL,
    token_settings                VARCHAR(2000)        NOT NULL
);

--rollback DROP TABLE IF EXISTS registered_client CASCADE;


--changeset system:v0.0.0-create-users-table
CREATE TABLE IF NOT EXISTS users
(
    id         CHAR(26) PRIMARY KEY,
    email      VARCHAR(255) NOT NULL,
    pwd        VARCHAR(255) NOT NULL,
    is_active  BOOLEAN   DEFAULT TRUE,
    roles      VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    is_deleted BOOLEAN   DEFAULT FALSE
);
--rollback DROP TABLE IF EXISTS users CASCADE;

--changeset system:v0.0.0-create-user_detail-table
CREATE TABLE IF NOT EXISTS users_detail
(
    id            CHAR(26) PRIMARY KEY,
    users_pk      CHAR(26),
    name          VARCHAR(20),
    phone         VARCHAR(20),
    profile_image VARCHAR(255)
);
--rollback DROP TABLE IF EXISTS users_detail CASCADE;

--changeset system:v0.0.0-create-passkey_credentials-table
CREATE TABLE IF NOT EXISTS passkey_credentials
(
    id              CHAR(26) PRIMARY KEY,
    users_Pk        CHAR(26),
    credential_id   BYTEA NOT NULL,
    public_key      BYTEA NOT NULL,
    signature_count BIGINT    DEFAULT 0,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--rollback DROP TABLE IF EXISTS users_detail CASCADE;

--changeset system:v0.0.0-add-foreign-keys
ALTER TABLE users_detail
    ADD CONSTRAINT fk_users_detail_users
        FOREIGN KEY (users_pk) REFERENCES users (id);

ALTER TABLE passkey_credentials
    ADD CONSTRAINT fk_passkey_credentials_users
        FOREIGN KEY (users_pk) REFERENCES users (id);
--changeset system:v0.0.0-add-unique-constraints

ALTER TABLE users
    ADD CONSTRAINT unique_email UNIQUE (email);
ALTER TABLE passkey_credentials
    ADD CONSTRAINT unique_credential_id UNIQUE (credential_id)
--rollback ALTER TABLE users DROP CONSTRAINT unique_email;



