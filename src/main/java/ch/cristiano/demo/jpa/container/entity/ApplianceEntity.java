package ch.cristiano.demo.jpa.container.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "appliance")
public class ApplianceEntity implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID")
   private int id;

   @Column(name = "STATUS")
   private int status;

   @ManyToOne(fetch = FetchType.LAZY)
   private OrganizationEntity organization;

   public ApplianceEntity() {
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public void setOrganization(OrganizationEntity organization) {
      this.organization = organization;
   }

   public int getOrganizationId() {
      return this.organization.getId();
   }

   public String getOrganizationName() {
      return this.organization.getDisplayName();
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      }
      if (!(o instanceof ApplianceEntity)) {
         return false;
      }
      ApplianceEntity applianceEntity = (ApplianceEntity) o;
      return id == applianceEntity.id && status == applianceEntity.status
            && Objects.equals(organization, applianceEntity.organization);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, status, organization);
   }
}
