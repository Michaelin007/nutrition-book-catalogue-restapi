package book.management.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import book.management.model.User;
import book.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import book.management.exception.ResourceNotFoundException;
import book.management.model.Book;
import book.management.repository.BookRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class  BookController {
	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/books")
	
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Long bookId)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));
		return ResponseEntity.ok().body(book);
	}
	
        @RolesAllowed("ROLE_ADMIN")
	@PostMapping("/books")
	public Book createBook(@Valid @RequestBody Book book) {
		return bookRepository.save(book);
	}


         @RolesAllowed("ROLE_ADMIN")
	@PutMapping("/books/{id}")
	public ResponseEntity<Book> updateEmployee(@PathVariable(value = "id") Long bookId,
			@Valid @RequestBody Book bookDetails) throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));

		book.setAuthor(bookDetails.getAuthor());
		book.setPublishedYear(bookDetails.getPublishedYear());
		book.setFirstName(bookDetails.getFirstName());
		final Book updatedBook = bookRepository.save(book);
		return ResponseEntity.ok(updatedBook);
	}

	@DeleteMapping("/books/{id}")
	public Map<String, Boolean> deleteBook(@PathVariable(value = "id") Long bookId)
			throws ResourceNotFoundException {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));

		bookRepository.delete(book);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
