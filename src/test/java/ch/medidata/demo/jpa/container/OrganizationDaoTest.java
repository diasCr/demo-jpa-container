package ch.medidata.demo.jpa.container;

import static org.mockito.ArgumentMatchers.any;
import javax.persistence.EntityManager;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ch.medidata.demo.jpa.container.entity.OrganizationEntity;

@ExtendWith(MockitoExtension.class)
public class OrganizationDaoTest {

    @Mock
    EntityManager entityManagerMock;

    @Spy
    Validator validatorSpy;

    @InjectMocks
    OrganizationDao organizationDao;

    @Test
    void getOrganizationById_entityFound_returnEntity() {
        // Arrange
        OrganizationEntity entity = new OrganizationEntity(10, "displayName");
        Mockito.when(entityManagerMock.find(any(), any())).thenReturn(entity);
        // Mockito.doReturn(entity).when(entityManagerMock).find(any(), any());

        // Act
        OrganizationEntity result = organizationDao.getOrganizationById(10);

        // Assert
        Assertions.assertNotNull(result);
        Mockito.verify(entityManagerMock).find(any(), any());
        Mockito.verify(validatorSpy).validateEntity(any());
    }

    @Test
    void getOrganizationById_entityFoundWithoutDisplayname_throwException() {
        // Arrange
        OrganizationEntity entity = new OrganizationEntity(10, "");
        // Mockito.when(entityManagerMock.find(any(), any())).thenReturn(entity);
        Mockito.doReturn(entity).when(entityManagerMock).find(any(), any());

        // Act
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> organizationDao.getOrganizationById(10));

        // Assert
        Mockito.verify(entityManagerMock).find(any(), any());
        Assertions.assertTrue("Entity displayname is empty.".equals(exception.getMessage()));
        Mockito.verify(validatorSpy).validateEntity(any());
    }

    @Test
    void getOrganizationById_entityNotFound_throwException() {
        // Arrange
        // Mockito.when(entityManagerMock.find(any(), any())).thenReturn(null);
        Mockito.doReturn(null).when(entityManagerMock).find(any(), any());

        // Act
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> organizationDao.getOrganizationById(20));

        // Assert
        Mockito.verify(entityManagerMock).find(any(), any());
        // Assertions.assertTrue("Entity not found".equals(exception.getMessage()));
        MatcherAssert.assertThat(exception.getMessage(), Matchers.equalTo("Entity not found."));
    }
}
