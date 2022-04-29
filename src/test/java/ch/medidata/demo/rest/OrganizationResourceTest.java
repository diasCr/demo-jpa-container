package ch.medidata.demo.rest;

import static org.mockito.ArgumentMatchers.any;
import java.net.URISyntaxException;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ch.medidata.demo.jpa.container.OrganizationDao;
import ch.medidata.demo.jpa.container.entity.OrganizationEntity;

@ExtendWith(MockitoExtension.class)
public class OrganizationResourceTest {

    @Mock
    OrganizationDao organizationDaoMock;

    @InjectMocks
    OrganizationResource organizationResource;

    @Test
    void createOrganization() throws URISyntaxException {
        // Arrange
        OrganizationEntity organizationEntity = new OrganizationEntity(100, "displayname");
        Mockito.when(organizationDaoMock.createOrganizationEntity(any())).thenReturn(organizationEntity);

        // Act
        Response response = organizationResource.createOrganization(new OrganizationEntity());
        OrganizationEntity result = (OrganizationEntity) response.getEntity();

        // Assert
        Mockito.verify(organizationDaoMock).createOrganizationEntity(any());
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getId());
    }
}
