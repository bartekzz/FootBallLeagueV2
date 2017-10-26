package common;

/**
 * This class mirrors the Stadium-table in DB
 */
public class Stadium {

    private Integer id;
    private String name;

    public Stadium() {
    }

    /**
     * This construct a stadium with a name
     * @param name the name of the stadium
     */
    public Stadium(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
