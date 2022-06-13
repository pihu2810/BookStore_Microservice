package com.book.Book.controller;

import com.book.Book.dto.BookDTO;
import com.book.Book.dto.ResponseDTO;
import com.book.Book.model.BookContact;
import com.book.Book.service.IBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController
{
    @Autowired
    private IBookService iBookServiceservice;

    // Ability to call api to insert Book record
    // Add Data to repo
    @PostMapping("/insert")
    public ResponseEntity<String> addBookToRepository(@Valid @RequestBody BookDTO bookDTO) {
        String newBook = iBookServiceservice.createBook(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("New Book Added in Book Store", newBook);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }

    // Ability to call api to get All Book record by token
    @GetMapping(value = "/getAll/{token}")
    public ResponseEntity<String> getAllBookData(@PathVariable String token) {
        List<BookContact> listOfBooks = iBookServiceservice.getAllBookData(token);
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully (:", listOfBooks);
        return new ResponseEntity(dto, HttpStatus.OK);
    }
    // Ability to call api to get Book record by token
    @GetMapping(value = "/getById/{token}")
    public ResponseEntity<String> getBookDataById(@PathVariable String token) {
        BookContact Book = iBookServiceservice.getBookDataById(token);
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully by id (:", Book);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    // Ability to call api to Delete Book record by token
    @DeleteMapping("/delete/{token}")
    public ResponseEntity<String> deleteRecordById(@PathVariable String token) {
        ResponseDTO dto = new ResponseDTO("Book Record deleted successfully", iBookServiceservice.deleteRecordById(token));
        return new ResponseEntity(dto, HttpStatus.OK);
    }
    // Ability to call api to Update Book record by token
    @PutMapping("/updateBookById/{token}")
    public ResponseEntity<String> updateRecordById(@PathVariable String token, @Valid @RequestBody BookDTO bookDTO) {
        BookContact updateRecord = iBookServiceservice.updateRecordById(token,bookDTO);
        ResponseDTO dto = new ResponseDTO(" Book Record updated successfully by Id", updateRecord);
        return new ResponseEntity(dto, HttpStatus.ACCEPTED);
    }
    // Ability to call api to Update Book record by quantity
    @PutMapping("/update/{token}/{quantity}")
    public ResponseEntity<ResponseDTO> updateBooksByQuantity(@PathVariable String token,@PathVariable int quantity){
        BookContact bookData=iBookServiceservice.updataBooksByQuantity(token,quantity);
        ResponseDTO responseDTO=new ResponseDTO("updated book data succesfully",bookData);
        return  new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    // Ability to call api to Search Book record by Name
    @GetMapping("searchByBookName/{name}")
    public ResponseEntity<ResponseDTO> getBookByName(@PathVariable("name") String name) {
        List<BookContact> listOfBooks = iBookServiceservice.getBookByName(name);
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully (:", listOfBooks);
        return new ResponseEntity(dto, HttpStatus.OK);
    }
    // Ability to call api to Sort in Ascending order Book record
    @GetMapping("/sortAsc")
    public ResponseEntity<ResponseDTO> getBooksInAscendingOrder() {
        List<BookContact> listOfBooks = iBookServiceservice.sortedListOfBooksInAscendingOrder();
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully (:", listOfBooks);
        return new ResponseEntity(dto, HttpStatus.OK);
    }
    // Ability to call api to Sort in Descending order Book record
    @GetMapping("/sortDesc")
    public ResponseEntity<ResponseDTO> getBooksInDescendingOrder() {
        List<BookContact> listOfBooks = iBookServiceservice.sortedListOfBooksInDescendingOrder();
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully (:", listOfBooks);
        return new ResponseEntity(dto, HttpStatus.OK);
    }
    // Ability to call api to Search Book record authorName
    @GetMapping("searchByAuthorName/{authorName}")
    public ResponseEntity<ResponseDTO> getBookByAuthorName(@PathVariable("authorName") String authorName) {
        List<BookContact> listOfBooks = iBookServiceservice.getBookByAuthorName(authorName);
        ResponseDTO dto = new ResponseDTO("Data retrieved successfully (:", listOfBooks);
        return new ResponseEntity(dto, HttpStatus.OK);
    }

    //---------------------------API for rest template---------------------------//

    @GetMapping("/getBook/{bookId}")
    public BookContact getbookByIdAPI(@PathVariable int bookId){
        System.out.println("Test");
        BookContact book = iBookServiceservice.getbookByIdAPI(bookId);
        return book;
    }
}
