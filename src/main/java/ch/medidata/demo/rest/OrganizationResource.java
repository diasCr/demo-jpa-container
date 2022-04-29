package ch.medidata.demo.rest;

import ch.medidata.demo.jpa.container.OrganizationDao;
import ch.medidata.demo.jpa.container.entity.ApplianceEntity;
import ch.medidata.demo.jpa.container.entity.OrganizationEntity;
import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/organizations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrganizationResource {

   @Inject
   private OrganizationDao organizationDao;

   @POST
   public Response createOrganization(OrganizationEntity organization) throws URISyntaxException {
      OrganizationEntity createdEntity = this.organizationDao.createOrganizationEntity(organization);
      return Response.created(new URI("/organizations/" + createdEntity.getId())).entity(createdEntity).build();
   }

   @GET
   @Path("/{id}")
   public Response getById(@PathParam("id") int id) {
      OrganizationEntity foundOrganizationEntity = this.organizationDao.getOrganizationById(id);
      return Response.ok().entity(foundOrganizationEntity).build();
   }

   @PUT
   @Path("/{id}")
   public Response updateOrganization(@PathParam("id") int id, OrganizationEntity organization) {
      this.organizationDao.updateOrganization(organization);
      return Response.ok().build();
   }

   @DELETE
   @Path("/{id}")
   public Response deleteOrganization(@PathParam("id") int id) {
      this.organizationDao.deleteById(id);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id}/appliances/{applianceid}")
   public Response getApplianceById(@PathParam("id") int id, @PathParam("applianceid") int applianceid) {
      ApplianceEntity foundApplianceEntity = this.organizationDao.getApplianceById(applianceid);
      return Response.ok().entity(foundApplianceEntity).build();
   }

   @DELETE
   @Path("/{id}/appliances/{applianceid}")
   public Response deleteApplianceById(@PathParam("id") int id, @PathParam("applianceid") int applianceid) {
      this.organizationDao.deleteApplianceById(applianceid);
      return Response.ok().build();
   }
}
