package comp3350.chefsnotes.persistence;

import comp3350.chefsnotes.objects.TagExistenceException;

public interface TagDBMSTools{
    // adds the specified tag 'tagname' to the tag database
    // fails if the tagname already exists
    // returns true on success, false on failure
    // throws an exception on failure
    boolean addTag(String tagname) throws TagExistenceException;

    // adds the specified tag 'tagname' to the tag database
    // fails if the tagname does not exist
    // returns true on success, false on failure
    // throws an exception on failure
    boolean removeTag(String tagname) throws TagExistenceException;

    // returns an array of all tags in the database
    // array is size 0 if the database is empty.
    String[] tagList();
}
