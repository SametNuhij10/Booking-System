package Services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import Entity.Customer;
import Repositories.CustomerRepository;

import java.util.List;

@ApplicationScoped
public class CustomerService {

    @Inject
    private CustomerRepository customerRepository;

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerRepository.create(customer);
    }

    @Transactional
    public Customer updateCustomer(Customer customer) {
        return customerRepository.update(customer);
    }

    @Transactional
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }
}
