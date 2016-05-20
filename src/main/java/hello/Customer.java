//package hello;
//
//import org.springframework.data.annotation.Id;
//
//import java.util.ArrayList;
//import java.util.List;
//
////new Collection
////@Document
//public class Customer {
//
////    @Id
//    private String id;
//    private String firstName;
//    private String lastName;
//    private Address address;
//
//    private List<Movie> filmek;
//
//    public Customer() {
//        filmek =new ArrayList<>();
//    }
//
//    public Customer(String firstName, String lastName) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        filmek =new ArrayList<>();
//    }
//
//
//    public Customer(String firstName, String lastName, Address address) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.address = address;
//        filmek =new ArrayList<>();
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        for (Movie movie : filmek) {
//            sb.append(movie.toString());
//        }
//        return String.format(
//                "Customer[id=%s, firstName='%s', lastName='%s' , filmek='%s']",
//                id, firstName, lastName, sb.toString());
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return firstName + lastName;
//    }
//
//    public List<Movie> getFilmek() {
//        return this.filmek;
//    }
//
//    public void addMovie(Movie m) {
//        this.filmek.add(m);
//    }
//}