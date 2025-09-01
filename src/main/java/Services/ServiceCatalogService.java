package Services;

import Entity.ServiceEntity;
import Entity.ServiceCategory;
import Repositories.ServiceEntityRepository;
import Repositories.ServiceCategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ServiceCatalogService {

    @Inject
    private ServiceEntityRepository serviceEntityRepository;

    @Inject
    private ServiceCategoryRepository serviceCategoryRepository;

    public ServiceEntity findServiceById(Long id) {
        return serviceEntityRepository.findById(id);
    }

    public List<ServiceEntity> getAllServices() {
        return serviceEntityRepository.findAll();
    }

    @Transactional
    public ServiceEntity createService(ServiceEntity service) {
        return serviceEntityRepository.create(service);
    }

    @Transactional
    public ServiceEntity updateService(ServiceEntity service) {
        return serviceEntityRepository.update(service);
    }

    @Transactional
    public void deleteService(ServiceEntity service) {
        serviceEntityRepository.delete(service);
    }

    public ServiceCategory findCategoryById(Long id) {
        return serviceCategoryRepository.findById(id);
    }

    public List<ServiceCategory> getAllCategories() {
        return serviceCategoryRepository.findAll();
    }

    @Transactional
    public ServiceCategory createCategory(ServiceCategory category) {
        return serviceCategoryRepository.create(category);
    }

    @Transactional
    public ServiceCategory updateCategory(ServiceCategory category) {
        return serviceCategoryRepository.update(category);
    }

    @Transactional
    public void deleteCategory(ServiceCategory category) {
        serviceCategoryRepository.delete(category);
    }
}
