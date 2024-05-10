package com.example.demo.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "correct_answers")
public class Answer {
	
	@Id
	private int id;
	
	private String answer;
	
	private int questions_id;
	
	private Timestamp created_at;
	
	private Timestamp updated_at;
}