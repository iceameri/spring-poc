CREATE TABLE oauth2_authorized_clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_registration_id VARCHAR(255),
    principal_name VARCHAR(255),
    access_token VARCHAR(1024),
    refresh_token VARCHAR(1024),
    expires_at TIMESTAMP,
    scopes VARCHAR(1024),
    UNIQUE KEY idx_client_registration_id_principal_name (client_registration_id, principal_name)
);