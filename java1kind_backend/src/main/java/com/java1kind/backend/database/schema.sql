-- 
-- Copyright © 2025 Devin B. Royal.
-- All Rights Reserved.
--

-- USERS
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE,
    password_hash TEXT,
    is_admin BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- REPOS
CREATE TABLE repos (
    id SERIAL PRIMARY KEY,
    repo_name VARCHAR(255) NOT NULL,
    owner_id INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- FILE VERSIONS
CREATE TABLE file_versions (
    id SERIAL PRIMARY KEY,
    repo_id INTEGER REFERENCES repos(id),
    file_path TEXT NOT NULL,
    version_number INTEGER NOT NULL,
    encrypted BOOLEAN DEFAULT TRUE,
    checksum VARCHAR(256),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TRANSACTIONS
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    stripe_payment_id VARCHAR(255) NOT NULL,
    amount_cents INTEGER NOT NULL,
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- DOWNLOAD LOG
CREATE TABLE download_logs (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    file_version_id INTEGER REFERENCES file_versions(id),
    ip_address VARCHAR(100),
    user_agent TEXT,
    downloaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
