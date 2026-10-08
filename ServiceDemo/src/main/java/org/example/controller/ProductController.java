package org.example.controller;

import org.example.dao.ProductDAO;
import org.example.dao.UserDAO1;
import org.example.entity.DTO.ProductMsg;
import org.example.entity.PageBean;
import org.example.entity.Product;
import org.example.otherEntity.ResponseResult;
import org.mongodb.morphia.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.bson.types.ObjectId;
import org.example.dao.AdminDAO;
import org.example.dao.ProductDAO;
import org.example.dto.PageDTO;
import org.example.dto.ProductDTO;
import org.example.dto.getProductPageDTO;
import org.example.entity.Admin;
import org.example.entity.Product;
import org.example.otherEntity.ResponseResult;
import org.example.vo.PageVO;
import org.example.vo.ProductVO;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    ProductDAO productDAO = new ProductDAO();

    @RequestMapping("/listAll")
    public ResponseResult listAll(@RequestBody getProductPageDTO pageDTO) {

        List<Product> products = productDAO.findPage(pageDTO.getName(), pageDTO.getOffset(), pageDTO.getPageSize());
        List<ProductVO> productVOList = new ArrayList<>();
        products.forEach(product -> {
            ProductVO productVO = new ProductVO(null, product.getName(), product.getPrice(), product.getSum(), product.getVisitCount(), product.getStatus(), product.getAddDate(), product.getInfo());
            productVO.setId(product.getId().toString());
            productVOList.add(productVO);
        });
        Integer count = (int) productDAO.count(pageDTO.getName());
        PageVO<List<ProductVO>> pageVO = new PageVO<>(pageDTO.getPageIndex(), pageDTO.getPageSize(), count, productVOList);
        return ResponseResult.success(pageVO);
    }

    @RequestMapping("/deleteByIds")
    public ResponseResult deleteByIds(@RequestBody List<String> ids) {
        System.out.println(ids);
        ids.forEach(id -> {
            productDAO.delete(id);
        });
        return ResponseResult.success();
    }

    @RequestMapping("/listAllData")
    public ResponseResult listAllData() {
//        return ResponseResult.success(byNameAndPwd);
        List<Product> products = productDAO.findAll();
        List<ProductVO> productVOList = new ArrayList<>();
        products.forEach(product -> {
            ProductVO productVO = new ProductVO(null, product.getName(), product.getPrice(), product.getSum(), product.getVisitCount(), product.getStatus(), product.getAddDate(), product.getInfo());
            productVO.setId(product.getId().toString());
            productVOList.add(productVO);
        });
        return ResponseResult.success(productVOList);
    }

    @RequestMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestParam String id) {
        System.out.println(id);
        Product product = productDAO.findById(id);
        int status = 0;
        if (product.getStatus() == 0) {
            status = 1;
        }
        Product product1 = new Product(new ObjectId(id), null, 0.0, null, null, status, null, null);
        productDAO.update(product1);
        return ResponseResult.success();
    }

    @RequestMapping("/add")
    public ResponseResult add(@RequestBody Product product) {
//        System.out.println(product);
        product.setAddDate(new Date());
        productDAO.insertProduct(product);
        return ResponseResult.success();
    }

    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestParam String productId) {
        System.out.println(productId);
        productDAO.delete(productId);
        return ResponseResult.success();
    }

    @RequestMapping("/edit")
    public ResponseResult edit(@RequestBody ProductDTO productDTO) {
        Product product = new Product(new ObjectId(productDTO.getId()), productDTO.getName(), productDTO.getPrice(), productDTO.getSum(), productDTO.getVisitCount(), productDTO.getStatus(), productDTO.getAddDate(), productDTO.getInfo());
        productDAO.update(product);
        return ResponseResult.success();
    }

    @GetMapping("/getById/{id}")
    public ResponseResult getById(@PathVariable String id) {
        Product product = productDAO.findById(id);
        ProductVO productVO = new ProductVO(product.getId().toString(), product.getName(), product.getPrice(), product.getSum(), product.getVisitCount(), product.getStatus(), product.getAddDate(), product.getInfo());
        return ResponseResult.success(productVO);
    }

    //数据看板商品及用户信息展示
    @GetMapping("/getmsg")
    public ResponseResult getmsg() {
        ProductDAO productDAO = new ProductDAO();
        UserDAO1 userDAO1 = new UserDAO1();
        ProductMsg productMsg = new ProductMsg();
        productMsg.setUserSum((int) userDAO1.count());
        productMsg.setProSum((int) productDAO.count());
        return ResponseResult.success(productMsg);
    }

    //热销商品分页展示
    @RequestMapping("/saleload")
    public ResponseResult saleload(@RequestParam("pageNum") String pageNum, @RequestParam("pageSize") String pageSize) {

        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.salefindPage((Integer.parseInt(pageNum) - 1) * Integer.parseInt(pageSize), Integer.parseInt(pageSize));
        Long total = productDAO.count();
        PageBean<Product> page = new PageBean<Product>();
        page.setTotal(total);
        page.setPageSize(Integer.parseInt(pageSize));
        page.setPageNum(Integer.parseInt(pageNum));
        page.setList(products);
        return ResponseResult.success(page);

    }
    @GetMapping("/visit")
    public ResponseResult visit() throws UnknownHostException {
        ProductDAO dao=new ProductDAO();
        List<Long> list=new ArrayList<>();
        Long count1=dao.countByVisit(0,100);
        Long count2=dao.countByVisit(100,1000);
        Long count3=dao.countByVisit(1000,5000);
        Long count4=dao.countByVisit(5000,99999);
        list.add(count1);
        list.add(count2);
        list.add(count3);
        list.add(count4);
        return ResponseResult.success(list);
    }
    @GetMapping("/top5")
    public ResponseResult top5() throws UnknownHostException {
        ProductDAO dao=new ProductDAO();
        List<Product> list = dao.sortByVisit();
        return ResponseResult.success(list);
    }
    //待补货商品分页展示
    @RequestMapping("/addload")
    public ResponseResult addload(@RequestParam("pageNum") String pageNum, @RequestParam("pageSize") String pageSize) {

        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.addfindPage((Integer.parseInt(pageNum) - 1) * Integer.parseInt(pageSize), Integer.parseInt(pageSize));
        Long total = productDAO.addcount();
        PageBean<Product> page = new PageBean<Product>();
        page.setTotal(total);
        page.setPageSize(Integer.parseInt(pageSize));
        page.setPageNum(Integer.parseInt(pageNum));
        page.setList(products);
        return ResponseResult.success(page);

    }
}
