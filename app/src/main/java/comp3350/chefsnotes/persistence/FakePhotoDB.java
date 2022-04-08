package comp3350.chefsnotes.persistence;

import comp3350.chefsnotes.objects.Photo;

// perhaps a dummy class for fake implementation
public class FakePhotoDB implements PhotoDBMSTools{
    @Override
    public Photo[] getAllPhotos() {
        return new Photo[0];
    }

    @Override
    public boolean addPhoto(String pathname) {
        return false;
    }

    @Override
    public boolean removePhoto(String pathname) {
        return false;
    }

    @Override
    public boolean setReference(String pathname, int ref) {
        return false;
    }
}
