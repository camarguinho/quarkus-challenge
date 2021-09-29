package com.santander.cloudbr.games.challenges;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

@Path("/books")
@Produces(APPLICATION_JSON)
@Tag(name = "books")
public class BookResource {

    private static final Logger LOGGER = Logger.getLogger(BookResource.class);

    @Inject
    BookService service;

    @Operation(summary = "Returns all books")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class, type = SchemaType.ARRAY)))
    @Counted(name = "countGetAllBooks", description = "Counts how many times the getAllBooks method has been invoked")
    @Timed(name = "timeGetAllBooks", description = "Times how long it takes to invoke the getAllBooks method", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/")
    public Response getAllBooks() {
        List<Book> bookList = service.findAllBooks();
        LOGGER.debug("Listing all books " + bookList);
        return Response.ok(bookList).build();
    }


    @Operation(summary = "Find book by name")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class)))
    @APIResponse(responseCode = "204", description = "Book not found for a given identifier")
    @Counted(name = "countGetBookByName", description = "Counts how many times the getBookByName method has been invoked")
    @Timed(name = "timeGetBookByName", description = "Times how long it takes to invoke the getBookByName method", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/{name}")
    public Response getBookByName(@Parameter(description = "Book identifier", required = true) @PathParam("name") String bookName) {
        Book book = service.findBookByName(bookName);
        if (book == null) {
            LOGGER.debug("Book not found for name " + bookName);
            return Response.noContent().build();    
        } else {
            LOGGER.debug("Found book " + book);
            return Response.ok(book).build();
        }
    }

    @Operation(summary = "Find books by publication year between")
    @APIResponse(responseCode = "200", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class, type = SchemaType.ARRAY)))
    @Counted(name = "countGetBookByPublicationYearBetween", description = "Counts how many times the getBookByPublicationYearBetween method has been invoked")
    @Timed(name = "timeGetBookByPublicationYearBetween", description = "Times how long it takes to invoke the getBookByPublicationYearBetween method", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/{lowerYear}/{higherYear}")
    public Response getBookByPublicationYearBetween(
        @Parameter(description = "Book identifier lower year", required = true) @PathParam("lowerYear") Integer lowerYear,
        @Parameter(description = "Book identifier higher year", required = true) @PathParam("higherYear") Integer higherYear
    ) {
        List<Book> books = service.findBookByPublicationYearBetween(lowerYear, higherYear);
        LOGGER.debug("Found book(s) " + books);
        return Response.ok(books).build();
    }

}