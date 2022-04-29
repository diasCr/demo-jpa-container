package ch.medidata.demo.jsf.beans;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import ch.medidata.demo.jpa.container.OrganizationDao;
import ch.medidata.demo.jpa.container.entity.OrganizationEntity;

@Named
@RequestScoped
public class OrganizationBean {

    @Inject
    private OrganizationDao organizationDao;

    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<OrganizationEntity> getOrganizations() {
        return this.organizationDao.getAllOrganizations();
    }
}
