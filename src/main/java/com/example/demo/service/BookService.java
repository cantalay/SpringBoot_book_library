package com.example.demo.service;

import com.example.demo.model.BookModel;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public List<BookModel> findAll(){
        return bookRepository.findAll();
    }

    public Optional<BookModel> findById(Integer id){
        return bookRepository.findById(id);
    }

    public BookModel save(BookModel bookModel){
        return bookRepository.save(bookModel);
    }

    public void deleteById(Integer id){
        bookRepository.deleteById(id);
    }

}
