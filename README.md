# SEMITA Application

## Overview

SEMITA is a web application designed for defect tracking management. This document provides the prerequisites needed to run the application locally.

## Prerequisites

To run the SEMITA application, you'll need the following:

### 1. Java Development Kit (JDK) 17

The application is built using Java 17. Ensure you have JDK 17 installed on your machine. You can download it from the [official Oracle website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or use an OpenJDK distribution.

### 2. Apache Maven or Gradle

The project uses Gradle for dependency management and build automation. Make sure Gradle is installed on your system. You can download it from the [official Gradle website](https://gradle.org/install/).

### 3. MySQL Database

The application uses MySQL as its database. Ensure you have MySQL installed and running. You can download it from the [official MySQL website](https://dev.mysql.com/downloads/mysql/).

**Database Configuration:**
- Database URL: `jdbc:mysql://localhost:3306/semita?useSSL=false`
- Username: `root`
- Password: `admin`

### 4. SMTP Server for Email

The application requires an SMTP server to send emails. Ensure you have a Email account, setup `App Password` and update the credentials in the `.env` file.

### 5. Environment Variables

Create a `.env` file in the root directory of your project to securely store sensitive information and configuration settings. Here's a sample `.env` file format:

```env
EMAIL_USERNAME=your-email@example.com
EMAIL_PASSWORD= App Password

```
### Contact Information
For details on obtaining or configuring the .env file, please contact:

- Name: `Narendiran Sathuzan`
- Phone No: `+94770184514`
- Email: `n.sathuzan@gmail.com`
