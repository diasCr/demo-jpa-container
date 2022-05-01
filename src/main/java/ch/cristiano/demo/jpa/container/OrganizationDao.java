package ch.cristiano.demo.jpa.container;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import ch.cristiano.demo.jpa.container.entity.ApplianceEntity;
import ch.cristiano.demo.jpa.container.entity.OrganizationEntity;

@RequestScoped
public class OrganizationDao {

   @PersistenceContext(name = "dashboardPU")
   private EntityManager entityManager;

   @Inject
   private Validator validator;

   public OrganizationEntity getOrganizationById(int id) {
      OrganizationEntity foundOrganizationEntity = this.entityManager.find(OrganizationEntity.class, id);
      if (foundOrganizationEntity != null) {
         this.validator.validateEntity(foundOrganizationEntity);
         outputInfos(foundOrganizationEntity);
         return foundOrganizationEntity;
      } else {
         throw new RuntimeException("Entity not found.");
      }
   }

   @Transactional
   public OrganizationEntity createOrganizationEntity(OrganizationEntity newOrganization) {
      this.validator.validateEntity(newOrganization);
      OrganizationEntity organizationEntityToBeCreated = new OrganizationEntity(newOrganization.getId(),
            newOrganization.getDisplayName());
      for (ApplianceEntity appliance : newOrganization.getAppliances()) {
         organizationEntityToBeCreated.addAppliance(appliance);
      }
      this.entityManager.persist(organizationEntityToBeCreated);
      this.entityManager.flush();
      outputInfos(organizationEntityToBeCreated);
      return organizationEntityToBeCreated;
   }

   public OrganizationEntity queryOrganization(String displayName) {
      TypedQuery<OrganizationEntity> query = this.entityManager.createQuery(
            "SELECT org FROM OrganizationEntity org WHERE org.displayName = :displayName", OrganizationEntity.class);
      query.setParameter("displayName", displayName);
      OrganizationEntity foundOrganizationEntity = query.getSingleResult();
      outputInfos(foundOrganizationEntity);
      return foundOrganizationEntity;
   }

   public List<OrganizationEntity> getAllOrganizations() {
      TypedQuery<OrganizationEntity> query = this.entityManager.createQuery("SELECT org FROM OrganizationEntity org",
            OrganizationEntity.class);
      return query.getResultList();
   }

   @Transactional
   public void deleteById(int id) {
      OrganizationEntity foundEntity = this.getOrganizationById(id);
      this.entityManager.remove(foundEntity);
      outputInfos(foundEntity);
   }

   @Transactional
   public void deleteApplianceById(int id) {
      ApplianceEntity applianceEntity = this.getApplianceById(id);
      applianceEntity.setOrganization(null);
      this.entityManager.remove(applianceEntity);
   }

   public ApplianceEntity getApplianceById(int id) {
      ApplianceEntity foundApplianceEntity = this.entityManager.find(ApplianceEntity.class, id);
      return foundApplianceEntity;
   }

   @Transactional
   public void updateOrganization(OrganizationEntity organization) {
      OrganizationEntity organizationEntityToBeUpdated = new OrganizationEntity(organization.getId(),
            organization.getDisplayName());
      for (ApplianceEntity appliance : organization.getAppliances()) {
         organizationEntityToBeUpdated.addAppliance(appliance);
      }
      this.entityManager.merge(organizationEntityToBeUpdated);
      outputInfos(organizationEntityToBeUpdated);
   }

   private void outputInfos(OrganizationEntity entity) {
      System.out.println("id: " + entity.getId());
      System.out.println("displayName: " + entity.getDisplayName());
   }
}
