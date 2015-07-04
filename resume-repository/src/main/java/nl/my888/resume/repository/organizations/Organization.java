package nl.my888.resume.repository.organizations;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import nl.my888.resume.repository.common.FieldConstants;
import nl.my888.resume.repository.locations.Address;

@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = FieldConstants.LABEL_LENGTH)
    private String name;

    @ElementCollection
    private Collection<Address> addresses = new ArrayList<>();

    protected Organization() {
        // JPA only!
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Address> getAddresses() {
        return addresses;
    }

    // test only!
    void setId(Long id) {
        this.id = id;
    }
}
