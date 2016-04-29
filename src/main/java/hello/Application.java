//package hello;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
////default mongo db access, name: test, host: localhost
//@SpringBootApplication
//public class Application implements CommandLineRunner {
//
//    //repositories can be used for JPA with @Entities later
//    @Autowired
//    private CustomerRepository repository;
//    @Autowired
//    private MovieRepository mRepo;
//
//    public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // delete all from collections
//        repository.deleteAll();
//        mRepo.deleteAll();
//
//        // save a couple of customers
//        repository.save(new Customer("Alice", "Smith"));
//        repository.save(new Customer("Bob", "Smith"));
//
//        Address address = new Address("Hungary");
//        Movie movie = new Movie("Titanic", 140);
//
//        //unfortunately, there is no cascade in mongoDB, i have to save the movie before
//        //System.out.println("Ezt írja ki a save:"+mRepo.save(movie));
//        Customer barna = new Customer("Barna", "Burom", address);
//
//        barna.addMovie(movie);
//        movie.addWatcher(barna);
//
//        repository.save(barna);
////        mRepo.insert(movie);
//
//        // fetch all customers
//        System.out.println("Customers found with findAll():");
//        System.out.println("-------------------------------");
//        for (Customer customer : repository.findAll()) {
//            System.out.println(customer);
//        }
//        System.out.println();
//
//        // fetch an individual customer
//        System.out.println("Customer found with findByFirstName('Alice'):");
//        System.out.println("--------------------------------");
//        System.out.println(repository.findByFirstName("Alice"));
//
//        System.out.println("Customers found with findByLastName('Smith'):");
//        System.out.println("--------------------------------");
//        for (Customer customer : repository.findByLastName("Smith")) {
//            System.out.println(customer);
//        }
//        Customer me = repository.findByFirstNameAndLastName("Barna", "Burom");
//        System.out.println("Ez én vagyok: :) " + me.getName());
//
//        System.out.println("Ez is én vagyok: :) " + repository.findOne(me.getId()));
//
//        System.out.println("Ez is én vagyok(address): :) " + repository.findByShippingAddresses(address));
//
////        System.out.println("Ez pedig a film : "+mRepo.findAll().get(0));
//        System.in.read();
//
//    }
//
//}
