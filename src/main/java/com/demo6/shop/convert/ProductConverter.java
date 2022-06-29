package com.demo6.shop.convert;

import com.demo6.shop.dao.ProductDao;
import com.demo6.shop.dao.SaleDao;
import com.demo6.shop.entity.Category;
import com.demo6.shop.entity.Product;
import com.demo6.shop.entity.Sale;
import com.demo6.shop.dto.CategoryDTO;
import com.demo6.shop.dto.ProductDTO;
import com.demo6.shop.dto.SaleDTO;
import com.demo6.shop.service.ProductService;
import com.demo6.shop.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Date;

@Component
public class ProductConverter {
    @Autowired
    private SaleDao saleDao;
    @Autowired
    private ProductDao productDao;
@Autowired
private SaleConverter saleConverter;
    @Transactional
    public ProductDTO toDto(Product productEntity) {

        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductId(productEntity.getProductId());
        productDTO.setProductName(productEntity.getProductName());
        productDTO.setPrice(productEntity.getPrice());
        // productDTO.setSaleDTO(productEntity.getSale());
        productDTO.setImage(productEntity.getImage());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setQuantity(productEntity.getQuantity());
        productDTO.setExpirationDate(productEntity.getExpiredDate());

        SaleDTO sale = new SaleDTO();
        if (saleDao.findOne(productEntity.getSale().getSaleId()) == null) {
            Product product = productDao.findById(productEntity.getProductId());
            sale.setSaleId("1");
            sale.setSalePercent(0);
            Sale sale1 = new Sale();
            sale1.setSaleId("1");
            sale1.setSalePercent(0);
            product.setSale(sale1);
        } else {
            sale.setSaleId(productEntity.getSale().getSaleId());
            sale.setSalePercent(productEntity.getSale().getSalePercent());
        }
        productDTO.setSaleDTO(sale);
        // productDTO.setCategoryDTO(productEntity.getCategory());
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(productEntity.getCategory().getCategoryId());
        categoryDTO.setCategoryName(productEntity.getCategory().getCategoryName());
        productDTO.setCategoryDTO(categoryDTO);
        return productDTO;
    }

    public Product toEntity(ProductDTO dto) {
        Product result = new Product();
        result.setProductId(dto.getProductId());
        result.setProductName(dto.getProductName());
        result.setDescription(dto.getDescription());
        result.setImage(dto.getImage());
        result.setPrice(dto.getPrice());
        result.setQuantity(dto.getQuantity());
        result.setExpiredDate(dto.getExpirationDate());
        // result.setCreateDate(new Date(new java.util.Date().getTime()));
        Sale sale = new Sale();
        sale.setSaleId(dto.getSaleDTO().getSaleId());
        sale.setSalePercent(dto.getSaleDTO().getSalePercent());
        result.setSale(sale);
        Category category = new Category();
        category.setCategoryId(dto.getCategoryDTO().getCategoryId());
        category.setCategoryName(dto.getCategoryDTO().getCategoryName());
        result.setCategory(category);
        return result;
    }

    public Product toEntity(Product result, ProductDTO dto) {
        result.setQuantity(dto.getQuantity());
        result.setPrice(dto.getPrice());
        result.setProductId(dto.getProductId());
        result.setProductName(dto.getProductName());
        result.setDescription(dto.getDescription());
        return result;
    }
}
