package hello;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//new collection
@Document
public class Movie {

    @Id
    private String id;

    private String title;
    private long length;

    public Movie(){
    }

    public Movie(String title,long l){
        this.title=title;
        this.length=l;
    }

    @Override
    public String toString(){
        return String.format("Movie[id=%s, title=%s, length=%d]",id,title,length);
    }

}
