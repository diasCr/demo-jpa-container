package ch.cristiano.demo.jpa.container.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "organization")
public class OrganizationEntity implements Serializable {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID")
   private int id;

   @Column(name = "DISPLAY_NAME")
   private String displayName;

   @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<ApplianceEntity> appliances = new ArrayList<>();

   public OrganizationEntity() {
   }

   public OrganizationEntity(int id, String displayName) {
      this.id = id;
      this.displayName = displayName;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getDisplayName() {
      return displayName;
   }

   public void setDisplayName(String displayName) {
      this.displayName = displayName;
   }

   public List<ApplianceEntity> getAppliances() {
      return appliances;
   }

   public void setAppliances(List<ApplianceEntity> appliances) {
      this.appliances = appliances;
   }

   public void addAppliance(ApplianceEntity appliance) {
      appliances.add(appliance);
      appliance.setOrganization(this);
   }

   public void removeAppliance(ApplianceEntity appliance) {
      appliances.remove(appliance);
      appliance.setOrganization(null);
   }
}
