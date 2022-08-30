package com.devfabio.dscatalog.services;

import com.devfabio.dscatalog.dto.CategoryDTO;
import com.devfabio.dscatalog.entities.Category;
import com.devfabio.dscatalog.repositories.CategoryRepository;
import com.devfabio.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true) // avoid locking in BD ggranted tha will connect to a DB
    public List <CategoryDTO> findAll() {
        List<Category> list = repository.findAll();
        return list.stream().map(el -> new CategoryDTO(el)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional <Category> obj =  repository.findById(id);
        Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        return new CategoryDTO(entity);
    }

    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);
    }
}
