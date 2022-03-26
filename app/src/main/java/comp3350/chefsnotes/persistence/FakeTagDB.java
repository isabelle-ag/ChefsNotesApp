package comp3350.chefsnotes.persistence;

import java.util.ArrayList;

import comp3350.chefsnotes.objects.TagExistenceException;

public class FakeTagDB implements TagDBMSTools{
    private ArrayList<String> tagList;

    public FakeTagDB(){
        tagList = new ArrayList<String>();
    }


    @Override
    public boolean addTag(String tagname) throws TagExistenceException {
        boolean result = false;

        if(!tagList.contains(tagname)){
            tagList.add(tagname);
            result = true;
        } else {
            throw new TagExistenceException("That tag already exists.");
        }
        return result;
    }

    @Override
    public boolean removeTag(String tagname) throws TagExistenceException {
        boolean result = false;

        if(!tagList.contains(tagname)){
            tagList.remove(tagname);
            result = true;
        } else {
            throw new TagExistenceException("That tag does not exist.");
        }

        return result;
    }

    @Override
    public String[] tagList() {
        return this.tagList.toArray(new String[0]);
    }
}
