package comp3350.chefsnotes.persistence;

import java.io.File;
import java.util.ArrayList;

import comp3350.chefsnotes.objects.Photo;

// perhaps a dummy class for fake implementation
public class FakePhotoDB implements PhotoDBMSTools{
    private ArrayList<String> photos;
    private ArrayList<Integer> refs;
    private int length;

    public FakePhotoDB(){
        photos = new ArrayList<String>();
        refs = new ArrayList<Integer>();
    }

    @Override
    public Photo[] getAllPhotos() {
        Photo[] result = new Photo[length];
        for(int i=0; i<length; i++){
            result[i] = new Photo(photos.get(i), refs.get(i));
        }
        return result;
    }

    @Override
    public boolean addPhoto(String pathname) {
        boolean result = false;

        if(!photos.contains(pathname)){
            photos.add(length,pathname);
            refs.add(length, 1);
            length++;
            result = true;
        }

        return result;
    }

    @Override
    public boolean removePhoto(String pathname) {
        boolean result = false;
        int ind = photos.indexOf(pathname);

        if(ind != -1){
            photos.remove(ind);
            refs.remove(ind);
            length--;
            new File(pathname).delete();    // remove the file
            result = true;
        }

        return result;
    }

    @Override
    public boolean setReference(String pathname, int ref) {
        boolean result = true;
        int ind = photos.indexOf(pathname);

        if(ind != -1){
            refs.set(ind,ref); // change value
            result = true;
        }

        return result;
    }
}
