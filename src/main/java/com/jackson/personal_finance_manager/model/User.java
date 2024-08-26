package com.jackson.personal_finance_manager.model;

import jakarta.persistence.Entity;

@Entity
public class User {
    String username;
    String name;
    String email;
    String password;
}
