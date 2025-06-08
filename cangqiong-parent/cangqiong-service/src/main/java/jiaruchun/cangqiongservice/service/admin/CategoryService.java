package jiaruchun.cangqiongservice.service.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jiaruchun.cangqiongservice.mapper.CategoryMapper;
import jiaruchun.common.utils.ThreadLocalUtil;
import jiaruchun.pojo.PageResult;
import jiaruchun.pojo.admin.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @CacheEvict(cacheNames = "category_type",allEntries = true)
    public void addCategory(Category category) {
        category.setCreateUser(ThreadLocalUtil.get());
        category.setUpdateUser(ThreadLocalUtil.get());
        category.setStatus(1);
        categoryMapper.addCategory(category);
    }

    @CacheEvict(cacheNames = "category_type",allEntries = true)
    public void upStatus(Integer status, Long id) {
        categoryMapper.upStatus(status,id);
    }

    public PageResult<Category> reAll(String name, Integer type, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Category> categories = categoryMapper.reAll(name, type);
        Page<Category> categories1 = (Page<Category>) categories;
        return new PageResult<>(categories1.getTotal(),categories1.getResult());
    }

    @CacheEvict(cacheNames = "category_type",allEntries = true)
    public void upCategory(Category category) {
        category.setUpdateUser(ThreadLocalUtil.get());
        categoryMapper.upCategory(category);
    }

    @CacheEvict(cacheNames = "category_type",allEntries = true)
    public void delete(Long id) {
        categoryMapper.delete(id);
    }

    public List<Category> ByType(Integer type) {
        return categoryMapper.ByType(type);
    }
}
