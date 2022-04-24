package comp3350.chefsnotes.persistence;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import comp3350.chefsnotes.objects.Photo;

public class PhotoListTest {
    PhotoDBMSTools pdb;
    PhotoList plist;

    @Before
    public void setup(){
        pdb = Mockito.mock(PhotoDBMSTools.class); // mock database
        plist = new PhotoList(pdb);               // inject mock
    }

    @Test
    public void testAddPhoto(){
        String name1 = "pathname";
        String name2 = "photothing";
        Photo p1 = new Photo(name1, 2);
        Photo p2 = new Photo(name1, 6);
        Photo p3 = new Photo(name2, 2);

        assertTrue(plist.addPhoto(p1));
        assertFalse(plist.addPhoto(p1));
        assertFalse(plist.addPhoto(p2));
        assertTrue(plist.addPhoto(p3));
    }

    @Test
    public void testReferences(){
        String name1 = "pathname";
        String name2 = "newnamefake46464646";
        Photo p1 = new Photo(name1, 2);
        plist.addPhoto(p1);

        plist.addReference(name1);
        assertEquals(p1.getRefCount(), 3);
        verify(pdb).setReference(name1,3);
        verify(pdb,times(0)).addPhoto(name1);

        plist.removeReference(name1);
        assertEquals(p1.getRefCount(),2);
        verify(pdb).setReference(name1, 2);
        verify(pdb,times(0)).removePhoto(name1);

        plist.addReference(name2);
        verify(pdb).addPhoto(name2);

        plist.removeReference(name2);
        verify(pdb).removePhoto(name2);

    }
}
