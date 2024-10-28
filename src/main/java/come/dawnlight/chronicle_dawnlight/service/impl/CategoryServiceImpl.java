package come.dawnlight.chronicle_dawnlight.service.impl;

import come.dawnlight.chronicle_dawnlight.mapper.CategoryMapper;
import come.dawnlight.chronicle_dawnlight.pojo.dto.CategoryDTO;
import come.dawnlight.chronicle_dawnlight.pojo.po.CategoryPO;
import come.dawnlight.chronicle_dawnlight.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void createCategory(CategoryDTO categoryDTO, String userId) {
        CategoryPO category = new CategoryPO();
        category.setName(categoryDTO.getName());
        category.setUserId(userId);
        categoryMapper.insert(category);
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        CategoryPO category = new CategoryPO();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        categoryMapper.update(category);
    }

    @Override
    public void deleteCategory(Long id, String userId) {
        // 可以增加权限检查
        categoryMapper.delete(id);
    }

    @Override
    public List<CategoryDTO> getCategoriesByUser(String userId) {
        List<CategoryPO> categories = categoryMapper.selectByUserId(userId);
        return categories.stream().map(category -> {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(category.getId());
            dto.setName(category.getName());
            return dto;
        }).collect(Collectors.toList());
    }
}
