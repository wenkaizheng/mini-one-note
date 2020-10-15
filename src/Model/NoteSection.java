package Model;


import java.io.Serializable;
import java.util.*;

/**
 * This class represents the section in model
 * @author Jiacheng Yang, Wenkai Zheng
 */
public class NoteSection extends Observable implements Serializable,
        Comparable<NoteSection> {
    /**
     * the id of this section
     */
    private final int id; // for section

    /**
     * a map that maps id to page object
     */
    private Map<Integer, NotePage> pageMap; // for page

    /**
     * the name of this section
     */
    private String name;

    /**
     * a counter for page id
     */
    private int nextPageID;


    /**
     * This is the constructor for this class
     * @param id a integer that is the id of this section
     * @param name a string that is the name of this section
     */
    public NoteSection(int id, String name) {
        this.name = name;
        this.id = id;
        pageMap = new HashMap<>();

        nextPageID = 0;

    }

    /**
     * This method resets the name of this section
     * @param name a string of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method returns a valid id and increments the id counter
     * @return a integer that is a valid id.
     */
    public int getNextPageIDWithIncrement() {
        return nextPageID++;
    }

    /**
     * This method returns the id of this section
     * @return a integer that is the id of this section
     */
    public int getID() {
        return id;
    }

    /**
     * This method returns the name of this section
     * @return a string that is the name of this section
     */
    public String getName() {
        return name;
    }

    /**
     * This method adds a page object to its page map
     * @param id the id for the page object
     * @param value a instance of NotePage
     */
    public void addPage(int id, NotePage value) {
        pageMap.put(id, value);
    }

    /**
     * This method sets the default page if
     * @return a integer that is a existing page's id
     */
    int getDefaultPageID() {
    	int i=-1;
    	for(Integer key:pageMap.keySet()) {
    		if(key <i||i==-1)
    			i=key;
    	}
    	return i;
    }

    /**
     * This method finds a page object by its id
     * @param key a integer that is a existing page's id
     * @return a page object.
     */
    public NotePage getPage(int key) {
        if (!pageMap.containsKey(key)) {
            System.out.println("we don't find this page");
            return null;
        } else {
            return pageMap.get(key);
        }
    }

    /**
     * This method removes a page by its id.
     * @param id a integer that is a existing page's id
     * @return a boolean that is true that the page is in the page map.
     * Otherwise false.
     */
    public boolean removePage(int id) {
        if (pageMap.containsKey(id)) {
            pageMap.remove(id);
            return true;
        }
        return false;
    }

    /**
     * This method checks if the id's corresponding page object is in this
     * section.
     * @param id a integer that is a existing page's id
     * @return a boolean that is true that the page is in the page map.
     * Otherwise false.
     */
    public boolean contains(int id) {
        return pageMap.containsKey(id);
    }


    /**
     * This method add a Observer to all shapes in its shapes list.
     *
     * @param o a Observer instance.
     */
    @Override
    public void addObserver(Observer o) {
        for (NotePage notePage : pageMap.values()) {
            notePage.addObserver(o);
        }
        super.addObserver(o);
    }

    /**
     * This method returns a collection of existing pages in page map
     * @return a collection of existing pages
     */
    public Collection<NotePage> getAllPage() {
        return pageMap.values();
    }

    /**
     * This method returns the page map
     * @return the page map
     */
    public Map<Integer, NotePage> getPageMap() {
        return pageMap;
    }

    /**
     * This method is used when sorting ther pages.
     * @param o a instance of NoteSection
     * @return the result of comparing their id.
     */
    @Override
    public int compareTo(NoteSection o) {
        return Integer.compare(this.id, o.id);
    }


}