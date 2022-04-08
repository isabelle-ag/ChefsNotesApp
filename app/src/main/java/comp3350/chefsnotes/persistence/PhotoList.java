package comp3350.chefsnotes.persistence;

import java.util.ArrayList;

import comp3350.chefsnotes.application.Services;
import comp3350.chefsnotes.objects.Photo;

// class acts as a simple in-memory version of the photo database.
// performs decision-making for photo deletion on 0 references.
// simpler usage by Recipe.java than database itself
public class PhotoList {
    private ArrayList<Photo> photos;
    private PhotoDBMSTools photodb;

    public PhotoList(){
        photos = new ArrayList<Photo>();
        photodb = Services.getPhotoPersistence();
    }

    public boolean addPhoto(Photo newPhoto){
        boolean result = false;

        if(!photos.contains(newPhoto)){
            photos.add(newPhoto);
            result = true;
        }

        return result;
    }

    // perhaps adds a photo that dne ?
    public boolean addReference(String photoname){
        boolean result = false;
        int ind = photos.indexOf(new Photo(photoname, 0));

        if(ind != -1){
            Photo target = photos.get(ind);
            target.addReference();
            result = true;
        }

        return result;
    }

    public boolean removeReference(String photoname){
        boolean result = false;

        return result;
    }
}
