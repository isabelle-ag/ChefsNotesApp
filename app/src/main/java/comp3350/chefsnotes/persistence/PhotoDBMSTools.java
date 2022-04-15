package comp3350.chefsnotes.persistence;

import comp3350.chefsnotes.objects.Photo;

public interface PhotoDBMSTools {
    // returns an array of all Photo objects
    Photo[] getAllPhotos();

    // adds the photo to the database if the name does not exist
    // sets the reference count to 1 by default, since 0 not allowed
    // fails if that pathname is already in the DB (should never happen in a real scenario)
    // returns true on success, false on failure
    boolean addPhoto(String pathname);

    // removes the photo from the database AND deletes the
    // file specified by 'pathname'
    // fails if the photo does not exist
    // fails if the file cannot be deleted
    // returns true on success, false on failure
    boolean removePhoto(String pathname);

    // adjusts the reference count to the photo if it exists
    // does NOT add the photo to the db if it does not exist
    // does NOT delete the photo if the reference count reaches zero
    // fails if the photo does not exist
    // returns true on success, false on failure
    boolean setReference(String pathname, int ref);

}
