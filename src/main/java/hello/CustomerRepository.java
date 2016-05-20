//package hello;
//
//import java.util.List;
//
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;
//
//public interface CustomerRepository extends MongoRepository<Customer, String> {
//
//    public Customer findByFirstName(String first);
//    public List<Customer> findByLastName(String lastName);
//    public Customer findByFirstNameAndLastName(String firstName, String lastName);
//
//    //there is no shippingAddresses variable in Customer, so i have to overwrite the default query generator from name
//    @Query("{'address': ?0 }")
//    public Customer findByShippingAddresses(Address address);
//}
