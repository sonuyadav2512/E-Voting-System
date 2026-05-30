-- ============================================
-- E-Voting System Database Schema
-- Database: evoting_db
-- ============================================

CREATE DATABASE IF NOT EXISTS evoting_db
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE evoting_db;

-- ============================================
-- 1. users
-- ============================================
CREATE TABLE IF NOT EXISTS users (
    user_id      INT AUTO_INCREMENT PRIMARY KEY,
    full_name    VARCHAR(100)  NOT NULL,
    email        VARCHAR(100)  UNIQUE NOT NULL,
    password     VARCHAR(255)  NOT NULL,
    role         ENUM('admin','voter') DEFAULT 'voter',
    is_approved  TINYINT(1)    DEFAULT 0,
    created_at   TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ============================================
-- 2. elections
-- ============================================
CREATE TABLE IF NOT EXISTS elections (
    election_id  INT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(200)  NOT NULL,
    description  TEXT,
    start_date   DATETIME      NOT NULL,
    end_date     DATETIME      NOT NULL,
    status       ENUM('upcoming','active','closed') DEFAULT 'upcoming',
    created_by   INT,
    created_at   TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_election_creator
        FOREIGN KEY (created_by) REFERENCES users(user_id)
        ON DELETE SET NULL
) ENGINE=InnoDB;

-- ============================================
-- 3. candidates
-- ============================================
CREATE TABLE IF NOT EXISTS candidates (
    candidate_id INT AUTO_INCREMENT PRIMARY KEY,
    election_id  INT NOT NULL,
    full_name    VARCHAR(100)  NOT NULL,
    party        VARCHAR(100),
    bio          TEXT,
    photo_path   VARCHAR(255),
    created_at   TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_candidate_election
        FOREIGN KEY (election_id) REFERENCES elections(election_id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================================
-- 4. votes
-- ============================================
CREATE TABLE IF NOT EXISTS votes (
    vote_id      INT AUTO_INCREMENT PRIMARY KEY,
    election_id  INT NOT NULL,
    voter_id     INT NOT NULL,
    candidate_id INT NOT NULL,
    voted_at     TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_vote (election_id, voter_id),
    CONSTRAINT fk_vote_election
        FOREIGN KEY (election_id) REFERENCES elections(election_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_vote_voter
        FOREIGN KEY (voter_id) REFERENCES users(user_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_vote_candidate
        FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id)
        ON DELETE CASCADE
) ENGINE=InnoDB;

-- ============================================
-- 5. audit_log
-- ============================================
CREATE TABLE IF NOT EXISTS audit_log (
    log_id     INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT,
    action     VARCHAR(255),
    details    TEXT,
    logged_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- ============================================
-- Default admin seed (OPTIONAL - Uncomment to use)
-- ============================================
-- If you want to use pre-defined admin credentials, uncomment the line below:
-- INSERT INTO users (full_name, email, password, role, is_approved)
-- VALUES ('Administrator', 'admin@evoting.com', SHA2('Admin@123', 256), 'admin', 1);

-- Otherwise, the application will show a Setup Wizard on first run to create your own admin account.
