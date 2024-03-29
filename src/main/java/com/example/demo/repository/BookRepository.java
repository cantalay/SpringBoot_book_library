package com.example.demo.repository;

import com.example.demo.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookModel,Integer> {

    Optional<BookModel> findByName(String bookName);


}
