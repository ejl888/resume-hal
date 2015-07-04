package nl.my888.resume.repository.organizations;

public class OrganizationFixtures {

    private OrganizationFixtures() {
        // util
    }

    public static Organization createValidOrganization(Long id) {
        final Organization result = new Organization();
        result.setId(id);

        result.setName("Organization-" + id);

        return result;
    }

}
