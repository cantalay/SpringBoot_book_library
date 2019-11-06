package com.example.demo.service;

import com.example.demo.model.BookModel;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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

    public ResponseEntity<BookModel> findById(Integer id){
        Optional<BookModel> bookModel = bookRepository.findById(id);
        if (!bookModel.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bookModel.get());
    }

    public ResponseEntity<BookModel> save(BookModel bookModel){
        Optional<BookModel> bookModelByName = bookRepository.findByName(bookModel.getName());
        if(bookModelByName.isPresent()){
           return ResponseEntity.badRequest().build();
        }else{
            return ResponseEntity.ok(bookRepository.save(bookModel));
        }
    }

    public ResponseEntity<BookModel> update(Integer id, BookModel bookModel){
        if (!bookRepository.findById(id).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bookRepository.save(bookModel));
    }

    public ResponseEntity deleteById(Integer id){
        if (!bookRepository.findById(id).isPresent()){
            return ResponseEntity.badRequest().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


    public ResponseEntity<Integer> getChapter(Integer id){
        Optional<BookModel> bookModel = bookRepository.findById(id);
        if (!bookModel.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bookModel.get().getChapterNumber());
    }

    public ResponseEntity<Integer> getPage(Integer id){
        Optional<BookModel> bookModel = bookRepository.findById(id);
        if (!bookModel.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bookModel.get().getPageNum());
    }

    public ResponseEntity<Integer> readPage(Integer id){
        Optional<BookModel> bookModel = bookRepository.findById(id);
        if (!bookModel.isPresent()){
           return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bookModel.get().getPageNum());
    }




}
