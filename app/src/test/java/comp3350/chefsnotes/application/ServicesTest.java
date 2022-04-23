package comp3350.chefsnotes.application;

import static org.junit.Assert.*;

import org.junit.Test;

public class ServicesTest {
    @Test
    public void testPersistences(){
        boolean fake = Services.MODE_FAKE;
        assertNotNull(Services.getRecipePersistence(fake));
        assertNotNull(Services.getRecipePersistence());
        assertNotNull(Services.getTagPersistence(fake));
        assertNotNull(Services.getTagPersistence());
        assertNotNull(Services.getPhotoPersistence(fake));
        assertNotNull(Services.getPhotoPersistence());
        assertNotNull(Services.getPhotoList());
    }
}
