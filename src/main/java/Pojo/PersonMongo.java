//package Pojo;
//
//import javax.persistence.*;
//
//@Entity
//@Table
//public class PersonMongo
//{
//
//    /** The person id. */
//    @Id
//    @Column
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private String personId;
//
//    /** The person name. */
//    @Column
//    private String personName;
//
//    /** The age. */
//    @Column
//    private int age;
//    // setters and getters.
//
//    public PersonMongo() {
//    }
//
//    public String getPersonId() {
//        return personId;
//    }
//
//    public void setPersonId(String personId) {
//        this.personId = personId;
//    }
//
//    public String getPersonName() {
//        return personName;
//    }
//
//    public void setPersonName(String personName) {
//        this.personName = personName;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    @Override
//    public String toString(){
//        return "Id: "+this.getPersonId()+" Név:"+this.getPersonName()+" Kor:"+this.getAge();
//    }
//}