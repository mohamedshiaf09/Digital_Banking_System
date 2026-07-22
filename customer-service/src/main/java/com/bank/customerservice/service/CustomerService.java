package com.bank.customerservice.service;

import com.bank.customerservice.dto.CustomerDTO;
import com.bank.customerservice.entity.Customer;
import com.bank.customerservice.exception.CustomerNotFoundException;
import com.bank.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return convertToDTO(savedCustomer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + customerId));
        return convertToDTO(customer);
    }


    public CustomerDTO updateCustomer(Long customerId, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + customerId));

        existingCustomer.setCustomerName(customerDTO.getCustomerName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setMobile(customerDTO.getMobile());
        existingCustomer.setAddress(customerDTO.getAddress());
        existingCustomer.setAadhaarNumber(customerDTO.getAadhaarNumber());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return convertToDTO(updatedCustomer);
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found with id: " + customerId));
        customerRepository.delete(customer);
    }


    private CustomerDTO convertToDTO(Customer customer) {
        return new CustomerDTO(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getMobile(),
                customer.getAddress(),
                customer.getAadhaarNumber()
        );
    }


    private Customer convertToEntity(CustomerDTO customerDTO) {
        return new Customer(
                customerDTO.getCustomerId(),
                customerDTO.getCustomerName(),
                customerDTO.getEmail(),
                customerDTO.getMobile(),
                customerDTO.getAddress(),
                customerDTO.getAadhaarNumber()
        );
    }
}

