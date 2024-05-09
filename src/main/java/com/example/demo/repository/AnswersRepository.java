package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Answer;

public interface AnswersRepository extends JpaRepository<Answer, Integer> {}
