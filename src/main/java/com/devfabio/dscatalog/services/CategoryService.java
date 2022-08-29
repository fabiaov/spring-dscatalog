package com.devfabio.dscatalog.services;

import com.devfabio.dscatalog.entities.Category;
import com.devfabio.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true) // avoid locking in BD ggranted tha will connect to a DB
    public List <Category> findAll() {
        return repository.findAll();
    }
}
