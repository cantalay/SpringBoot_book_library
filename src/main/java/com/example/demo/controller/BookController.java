package com.example.demo.controller;

import com.example.demo.model.BookModel;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookModel>> findAll(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @PostMapping
    public ResponseEntity createBook(@Valid @RequestBody BookModel bookModel) {
        return bookService.save(bookModel);
    }

    @GetMapping("/{id}/chapter/")
    public ResponseEntity<Integer> getChapter(@PathVariable Integer id){
        return bookService.getChapter(id);
    }

    @GetMapping("/{id}/chapter/{chapterNumber}/page/")
    public ResponseEntity<Integer> getPage(@PathVariable Integer id,@PathVariable Integer chapterNumber){
        return bookService.getPage(id);
    }

    @GetMapping("/{id}/chapter/{chapterNumber}/page/{pageNumber}")
    public ResponseEntity<Integer> readPage(@PathVariable Integer id,@PathVariable Integer chapterNumber,@PathVariable Integer pageNumber){
        return bookService.readPage(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookModel> findById(@PathVariable Integer id){
        return bookService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookModel> update(@PathVariable Integer id, @Valid @RequestBody BookModel bookModel){
        return bookService.update(id,bookModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        return bookService.deleteById(id);
    }

}
