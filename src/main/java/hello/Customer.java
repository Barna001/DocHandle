package hello;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

//new Collection
@Document
public class Customer {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private Address address;

    //Not embedded into Customer, only the reference is stored
    @DBRef
    private List<Movie> movies = new ArrayList<>();

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Customer(String firstName, String lastName, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Movie movie : movies) {
            sb.append(movie.toString());
        }
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s' , movies='%s']",
                id, firstName, lastName, sb.toString());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return firstName + lastName;
    }

    public List<Movie> getMovies() {
        return this.movies;
    }

    public void addMovie(Movie m) {
        this.movies.add(m);
    }
}