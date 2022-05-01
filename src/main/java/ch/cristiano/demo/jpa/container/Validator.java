package ch.cristiano.demo.jpa.container;

import javax.enterprise.context.RequestScoped;

import ch.cristiano.demo.jpa.container.entity.OrganizationEntity;

@RequestScoped
public class Validator {

    public void validateEntity(OrganizationEntity entity) {
        if (entity.getId() == 0) {
            throw new RuntimeException("Entity id is 0.");
        }
        if (entity.getDisplayName().isEmpty()) {
            throw new RuntimeException("Entity displayname is empty.");
        }
    }

}
