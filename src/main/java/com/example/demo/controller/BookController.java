package com.example.demo.controller;

import com.example.demo.model.BookModel;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;

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
        return ResponseEntity.ok(bookService.save(bookModel));
    }

    @GetMapping("/{id}/chapter/")
    public ResponseEntity<Integer> getChapter(@PathVariable Integer id){
        Optional<BookModel> bookModel = bookService.findById(id);
        if (!bookModel.isPresent()){
            ResponseEntity.badRequest().build();
        }
        //System.out.println(bookModel.get().getChapterNumber());
        return ResponseEntity.ok(bookModel.get().getChapterNumber());
    }

    @GetMapping("/{id}/chapter/{chapterNumber}/page/")
    public ResponseEntity<Integer> getPage(@PathVariable Integer id,@PathVariable Integer chapterNumber){
        Optional<BookModel> bookModel = bookService.findById(id);
        if (!bookModel.isPresent()){
            ResponseEntity.badRequest().build();
        }
        System.out.println(bookModel.get().getPageNum());
        return ResponseEntity.ok(bookModel.get().getPageNum());
    }
    @GetMapping("/{id}/chapter/{chapterNumber}/page/{pageNumber}")
    public ResponseEntity<Integer> readPage(@PathVariable Integer id,@PathVariable Integer chapterNumber,@PathVariable Integer pageNumber){
        Optional<BookModel> bookModel = bookService.findById(id);
        if (!bookModel.isPresent()){
            ResponseEntity.badRequest().build();
        }
        System.out.println(bookModel.get().getPageNum());
        return ResponseEntity.ok(bookModel.get().getPageNum());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookModel> findById(@PathVariable Integer id){
        Optional<BookModel> bookModel = bookService.findById(id);
        if (!bookModel.isPresent()){
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bookModel.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookModel> update(@PathVariable Integer id, @Valid @RequestBody BookModel bookModel){
        if (!bookService.findById(id).isPresent()){
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(bookService.save(bookModel));
    }

    @DeleteMapping("{/id}")
    public ResponseEntity delete(@PathVariable Integer id){
        if (!bookService.findById(id).isPresent()){
            ResponseEntity.badRequest().build();
        }
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
