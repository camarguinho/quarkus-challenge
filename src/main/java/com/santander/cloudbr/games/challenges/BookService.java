package com.santander.cloudbr.games.challenges;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(REQUIRED)
public class BookService {

    @Transactional(SUPPORTS)
    public List<Book> findAllBooks() {
        return Book.listAll();
    }

    @Transactional(SUPPORTS)
    public Book findBookByName(String bookName) {
        return Book.findByName(bookName);
    }

    
     @Transactional(SUPPORTS)
    public List<Book> findBookByPublicationYearBetween(Integer lowerYear, Integer higherYear) {
        return Book.findByPublicationYearBetween(lowerYear, higherYear);
    }

}