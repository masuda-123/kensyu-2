package com.example.demo.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
	
	@Id
	private int id;
	
	private String name;
	
	private String password;
	
	private Timestamp created_at;
	
	private Timestamp updated_at;
	
	private Timestamp deleted_at;
}
