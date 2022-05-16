package com.blogads.repository.mysql;

import com.blogads.entity.mysql.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findByNameContaining(String name, Pageable pageable);

    /**
     * @param name
     * @return true if exists else false
     * @author NhatPA
     */
    boolean existsByNameEqualsIgnoreCaseAndCategoryIdNot(String name, int id);

    boolean existsByNameEqualsIgnoreCase(String name);
}