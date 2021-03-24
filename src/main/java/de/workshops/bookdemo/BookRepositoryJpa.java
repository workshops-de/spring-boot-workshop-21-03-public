package de.workshops.bookdemo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BookRepositoryJpa extends CrudRepository<Book, Long> {

    public List<Book> findByAuthor(String author);
}
