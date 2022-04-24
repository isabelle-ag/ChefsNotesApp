package comp3350.chefsnotes.persistence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.chefsnotes.objects.TagExistenceException;

public class TagDBMSToolsTest {
    private TagDBMSTools tdb;

    @Before
    public void setup(){
        tdb = new FakeTagDB();
    }

    @Test
    public void testAllMethods(){
        String t1 = "thisTag";
        String t2 = "thatTag";
        String[] tagsOut = tdb.tagList();

        assertEquals(0, tagsOut.length);

        try {
            assertTrue(tdb.addTag(t1));
        } catch (TagExistenceException tee){
            fail();
        }
        assertThrows(TagExistenceException.class, ()->tdb.addTag(t1));
        tagsOut = tdb.tagList();
        assertEquals(1, tagsOut.length);

        assertThrows(TagExistenceException.class, ()->tdb.removeTag(t2));
        tagsOut = tdb.tagList();
        assertEquals(1, tagsOut.length);
        try{
            assertTrue(tdb.removeTag(t1));
        } catch (TagExistenceException tee){
            fail();
        }
        tagsOut = tdb.tagList();
        assertEquals(0,tagsOut.length);
    }
}
