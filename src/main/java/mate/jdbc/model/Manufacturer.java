package mate.jdbc.model;

public class Manufacturer {
    private long id;
    private String name;
    private String country;
    boolean isDeleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Manufacturer(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Manufacturer(long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.isDeleted = false;
    }

    public Manufacturer() {
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
