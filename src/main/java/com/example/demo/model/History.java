package com.example.demo.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "hisotires")
public class History {
	
	@Id
	private int id;
	
	private int user_id;
	
	private int point;
	
	private Timestamp created_at;
	
	private Timestamp deleted_at;
}
