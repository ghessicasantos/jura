CREATE DATABASE IF NOT EXISTS jura;
USE jura;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    cpf VARCHAR(20) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    cargo VARCHAR(100),
    login VARCHAR(80) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    profile_name VARCHAR(100),
    profile_type VARCHAR(30) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS users_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    cpf VARCHAR(20) NOT NULL,
    email VARCHAR(150) NOT NULL,
    cargo VARCHAR(100),
    login VARCHAR(80) NOT NULL,
    password VARCHAR(100) NOT NULL,
    profile_name VARCHAR(100),
    profile_type VARCHAR(30) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_name VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    team_owner_email VARCHAR(150) NOT NULL,
    created_at DATE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS teams_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_name VARCHAR(150) NOT NULL,
    description TEXT,
    team_owner_email VARCHAR(150) NOT NULL,
    created_at DATE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS team_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_member_email VARCHAR(150) NOT NULL,
    team_name VARCHAR(150) NOT NULL,
    member_status BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS team_members_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_member_email VARCHAR(150) NOT NULL,
    team_name VARCHAR(150) NOT NULL,
    member_status BOOLEAN NOT NULL DEFAULT TRUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS projects (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(150) NOT NULL UNIQUE,
    description TEXT,
    start_date DATE NOT NULL,
    finish_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    project_manager_email VARCHAR(150) NOT NULL,
    team_owner_name VARCHAR(150) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS project_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(150) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    finish_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    project_manager_email VARCHAR(150) NOT NULL,
    team_owner_name VARCHAR(150) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_title VARCHAR(150) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    finish_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    assigned_user_email VARCHAR(150) NOT NULL,
    project_name VARCHAR(150) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS tasks_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_title VARCHAR(150) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    finish_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    assigned_user_email VARCHAR(150) NOT NULL,
    project_name VARCHAR(150) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
