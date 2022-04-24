package comp3350.chefsnotes.persistence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.chefsnotes.objects.Photo;

public class PhotoDBMSToolsTest {
    PhotoDBMSTools pdb;

    @Before
    public void setup(){
        pdb = new FakePhotoDB();
    }

    @Test
    public void testAddAndRemoveAndList(){
        String name1 = "neverexists2828282828";
        String name2 = "fakenaming9797979797";
        Photo[] testOut = pdb.getAllPhotos();
        assertEquals(0, testOut.length);

        assertTrue(pdb.addPhoto(name1));
        assertFalse(pdb.addPhoto(name1));
        testOut = pdb.getAllPhotos();
        assertEquals(1, testOut.length);

        assertTrue(pdb.addPhoto(name2));
        testOut = pdb.getAllPhotos();
        assertEquals(2, testOut.length);

        assertTrue(pdb.removePhoto(name1));
        assertFalse(pdb.removePhoto(name1));
        testOut = pdb.getAllPhotos();
        assertEquals(1,testOut.length);
    }

    @Test
    public void testSetReference(){
        String name1 = "unlikely474747474747";
        pdb.addPhoto(name1);
        pdb.setReference(name1,20);

        Photo[] testOut = pdb.getAllPhotos();
        assertEquals(20,testOut[0].getRefCount());
    }


}
