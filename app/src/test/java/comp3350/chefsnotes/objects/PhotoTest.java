package comp3350.chefsnotes.objects;

import static org.junit.Assert.*;

import org.junit.Test;

public class PhotoTest {
    @Test
    public void testGetAndSet(){
        String name1 = "pathname";

        Photo ph = new Photo(name1, 2);
        assertEquals(name1, ph.getPathname());
        assertEquals(2, ph.getRefCount());

        ph.addReference();
        assertEquals(3,ph.getRefCount());

        ph.removeReference();
        assertEquals(2, ph.getRefCount());
    }

    @Test
    public void testEquals(){
        String name1 = "pathname";
        String name2 = "garbage";
        Photo p1 = new Photo(name1, 0);
        Photo p2 = new Photo(name1, 2);
        Photo p3 = new Photo(name2, 0);

        assertEquals(p1, p2);  // only names should be compared
        assertNotEquals(p1, p3);
    }
}
