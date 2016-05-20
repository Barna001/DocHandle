//package hello;
//
////import org.springframework.data.annotation.Id;
////import org.springframework.data.annotation.Transient;
////import org.springframework.data.mongodb.core.mapping.DBRef;
////import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.util.ArrayList;
//import java.util.List;
//
////new collection
////@Document
//public class Movie {
//
////    @Id
//    private String id;
////
////    private String title;
////    private long length;
//////    @DBRef
////    private List<String> megnezi;
////
//////    public Movie(){
//////        megnezi=new ArrayList<>();
//////    }
////
////    public Movie(String title,long l){
////        this.title=title;
////        this.length=l;
////        megnezi=new ArrayList<>();
////    }
////
////    @Override
////    public String toString(){
////        StringBuilder sb = new StringBuilder();
////        for (String customer : megnezi) {
////            sb.append(customer);
////        }
////        return String.format("Movie[id=%s, title=%s, length=%d, watched=%s]",id,title,length,sb.toString());
////    }
////
//////    public void addWatcher(Customer customer){
////        this.megnezi.add(customer);
////    }
//
//}
