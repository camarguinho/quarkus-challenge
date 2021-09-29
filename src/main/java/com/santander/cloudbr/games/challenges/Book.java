package com.santander.cloudbr.games.challenges;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
@Schema(description = "Book registry")
public class Book extends PanacheEntityBase {

    @Id
    private Integer id;

    public String name;

    public Integer publicationYear;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public static Book findByName(String name) {
        return find("name", name).firstResult();
    }

    public static List<Book> findByPublicationYearBetween(Integer lowerYear, Integer higherYear) {
        return find("publicationYear >= ?1 and publicationYear <= ?2", lowerYear, higherYear).list();
    }
}