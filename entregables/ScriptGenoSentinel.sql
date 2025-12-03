DROP DATABASE IF EXISTS genosentinel;
CREATE DATABASE genosentinel;
USE genosentinel;

CREATE TABLE Users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE User_Role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles(id) ON DELETE CASCADE
);

CREATE TABLE Patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    gender ENUM('Masculino', 'Femenino', 'Otro') NOT NULL,
    status ENUM('Activo', 'Seguimiento', 'Inactivo') DEFAULT 'Activo'
);

CREATE TABLE TumorType (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    system_affected VARCHAR(100)
);

CREATE TABLE ClinicalRecord (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    tumor_type_id BIGINT NOT NULL,
    diagnosis_date DATE NOT NULL,
    stage VARCHAR(20),
    treatment_protocol TEXT,
    FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE,
    FOREIGN KEY (tumor_type_id) REFERENCES TumorType(id) ON DELETE CASCADE
);

CREATE TABLE Gene (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    symbol VARCHAR(50) NOT NULL UNIQUE,
    full_name VARCHAR(150),
    function_summary TEXT
);

CREATE TABLE GeneticVariant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    gene_id BIGINT NOT NULL,
    chromosome VARCHAR(10) NOT NULL,
    position INT NOT NULL,
    reference_base CHAR(1) NOT NULL,
    alternate_base CHAR(1) NOT NULL,
    impact ENUM('Missense', 'Frameshift', 'Nonsense', 'Silent', 'Splice', 'Other'),
    FOREIGN KEY (gene_id) REFERENCES Gene(id) ON DELETE CASCADE
);

CREATE TABLE PatientVariantReport (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    variant_id BIGINT NOT NULL,
    detection_date DATE NOT NULL,
    allele_frequency DECIMAL(5,2),
    FOREIGN KEY (patient_id) REFERENCES Patient(id) ON DELETE CASCADE,
    FOREIGN KEY (variant_id) REFERENCES GeneticVariant(id) ON DELETE CASCADE
);