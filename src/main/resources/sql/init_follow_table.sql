
-- -----------------------------------------------------
-- Schema doctorpatient
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS doctorpatient;

-- -----------------------------------------------------
-- Schema doctorpatient
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS doctorpatient DEFAULT CHARACTER SET utf8 ;
USE doctorpatient;

-- -----------------------------------------------------
-- Table `patient`
-- -----------------------------------------------------
DROP TABLE IF EXISTS patient ;

CREATE TABLE IF NOT EXISTS patient (
  patient_id INT PRIMARY KEY AUTO_INCREMENT,
  lastname VARCHAR(45)  NOT NULL,
  firstname VARCHAR(45) NOT NULL,
  sex CHAR(1) NOT NULL,
  dob DATE NOT NULL,

  UNIQUE KEY demographics (lastname, firstname, sex, dob)
);

-- -----------------------------------------------------
-- Table `specialty`
-- -----------------------------------------------------
DROP TABLE IF EXISTS specialty ;

CREATE TABLE IF NOT EXISTS specialty (
  specialty_id INT PRIMARY KEY AUTO_INCREMENT,
  specialty VARCHAR(45) UNIQUE NOT NULL
);


-- -----------------------------------------------------
-- Table `hospital`
-- -----------------------------------------------------
DROP TABLE IF EXISTS hospital ;

CREATE TABLE IF NOT EXISTS hospital (
  hospital_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(45) UNIQUE NOT NULL
);


-- -----------------------------------------------------
-- Table `doctor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS doctor ;

CREATE TABLE IF NOT EXISTS doctor (
  doctor_id INT PRIMARY KEY AUTO_INCREMENT,
  lastname VARCHAR(45) NOT NULL,
  firstname VARCHAR(45) NOT NULL,
  new_patients TINYINT NOT NULL,
  specialty_id INT NOT NULL,
  hospital_id INT NOT NULL,

  CONSTRAINT fk_doctor_specialty FOREIGN KEY (specialty_id) REFERENCES specialty (specialty_id),
  CONSTRAINT fk_doctor_hospital FOREIGN KEY (hospital_id) REFERENCES hospital (hospital_id)
);

-- -----------------------------------------------------
-- Table `appointment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS appointment ;

CREATE TABLE IF NOT EXISTS appointment (
  patient_id INT NOT NULL AUTO_INCREMENT,
  doctor_id INT NOT NULL,
  appointment DATETIME NOT NULL,

  CONSTRAINT fk_appointment_patient FOREIGN KEY (patient_id) REFERENCES patient (patient_id),
  CONSTRAINT fk_appointment_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id)
);


DROP TABLE IF EXISTS rating ;

CREATE TABLE IF NOT EXISTS rating (
  patient_id INT NOT NULL AUTO_INCREMENT,
  doctor_id INT NOT NULL,
  rating DOUBLE NOT NULL,

  CONSTRAINT fk_rating_patient FOREIGN KEY (patient_id) REFERENCES patient (patient_id),
  CONSTRAINT fk_rating_doctor FOREIGN KEY (doctor_id) REFERENCES doctor (doctor_id)
);

