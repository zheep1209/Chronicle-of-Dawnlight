package come.dawnlight.chronicle_dawnlight.service;

import come.dawnlight.chronicle_dawnlight.pojo.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    void createCategory(CategoryDTO categoryDTO, String userId);
    void updateCategory(CategoryDTO categoryDTO);
    void deleteCategory(Long id, String userId);
    List<CategoryDTO> getCategoriesByUser(String userId);
}
