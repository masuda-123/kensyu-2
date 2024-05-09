package com.example.demo.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
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
	
	@Column
	private String name;
    
    @Column
	private String password;
    
    @Column
	private Timestamp created_at;
    
    @Column
	private Timestamp updated_at;
    
    @Column
	private Timestamp deleted_at;
}
