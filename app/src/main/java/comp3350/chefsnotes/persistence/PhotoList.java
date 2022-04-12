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

    // used to set up the PhotoList. Not for new photos from Recipe.
    // REQUIRED FOR MainActivity.onCreate() operations -- only way to add a photo
    // with a preexisting count of references.
    // DOES NOT ACCESS DATABASE
    public boolean addPhoto(Photo newPhoto){
        boolean result = false;

        if(!photos.contains(newPhoto)){
            photos.add(newPhoto);
            result = true;
        }

        return result;
    }

    // called by Recipe.addPhoto() and Recipe.clone()
    // if the photo does not exist, it is created with refCount = 1
    // reflects the change in the database
    // all operations succeed.
    public void addReference(String photoname){
        int ind = photos.indexOf(new Photo(photoname, 0));

        if(ind != -1){ // photo exists, add a reference
            Photo target = photos.get(ind);
            int newct = target.addReference();
            photodb.setReference(photoname,newct);    // make change in db

        } else { // photo dne, create with refCount 1
            photos.add(new Photo(photoname, 1)); // add photo to list
            photodb.addPhoto(photoname);            // add photo to db

        }

    }

    // called by Recipe.removePhoto() and Recipe.onDelete()
    public boolean removeReference(String photoname){
        boolean result = false;
        int ind = photos.indexOf(new Photo(photoname, 0));

        if(ind != -1){
            Photo target = photos.get(ind);
            int newct = target.removeReference();
            if(newct == 0){ // last reference, delete it
                photos.remove(ind);             // remove from list
                photodb.removePhoto(photoname); // remove from db

            } else { // just change reference
                photodb.setReference(photoname, newct);
            }
            result = true;
        }

        return result;
    }
}
