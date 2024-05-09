package com.example.demo.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
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
	
	@Column
	private String answer;
	
	@Column
	private int questions_id;
    
    @Column
	private Timestamp created_at;
    
    @Column
	private Timestamp updated_at;
}