package com.devfabio.dscatalog.services;

import com.devfabio.dscatalog.dto.ProductDTO;
import com.devfabio.dscatalog.entities.Product;
import com.devfabio.dscatalog.repositories.ProductRepository;
import com.devfabio.dscatalog.services.exceptions.DatabaseException;
import com.devfabio.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true) // avoid locking in BD ggranted tha will connect to a DB
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(el -> new ProductDTO(el));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional <Product> obj =  repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(entity, entity.getCategories());
    }

    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        //entity.setName(dto.getName());
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getOne(id);
            //entity.setName(dto.getName());
            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(("Id not found " + id));
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation! permission denied...");
        }
    }
}
