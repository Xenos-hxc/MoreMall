package org.example.dao;

import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.example.entity.Product;
import org.example.util.MorphiaPooUtil;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.util.List;

public class ProductDAO {

    public void insertProduct(Product product){
        Datastore datastore = MorphiaPooUtil.getDb();
        Key<Product> save = datastore.save(product);
        if (save!=null){
            System.out.println("插入成功");
        }
    }
    //删除
    public long delete(String id){
        Datastore datastore = MorphiaPooUtil.getDb();
        Query<Product> query=datastore.createQuery(Product.class);
        query.filter("_id",new ObjectId(id));
        WriteResult delete = datastore.delete(query);
        return delete.getN();
    }
    //根据id查询
    public Product findById(String id){
        Datastore datastore = MorphiaPooUtil.getDb();
        Query<Product> query=datastore.createQuery(Product.class);
        Product product = query.filter("_id", new ObjectId(id)).get();
        return product;
    }
    //查询所有
    public List<Product> findAll(){
        Datastore datastore = MorphiaPooUtil.getDb();
        Query<Product> query=datastore.createQuery(Product.class);
        return query.asList();
    }
    //分页查询
    public List<Product> findPage(String name,int skip,int limit){
        Datastore datastore= MorphiaPooUtil.getDb();
        Query<Product> query=datastore.createQuery(Product.class);
        // 添加模糊查询条件
        if (name != null && !name.isEmpty()) {
                query = query.field("name").contains(name);
        }
        FindOptions findOptions=new FindOptions();
        findOptions.skip(skip).limit(limit);
        List<Product> list=query.asList(findOptions);
        return list;
    }
    //统计总数
    public long count(String name){
        Datastore datastore= MorphiaPooUtil.getDb();
        Query<Product> query=datastore.createQuery(Product.class);
        if (name != null && !name.isEmpty()) {
            query = query.field("name").contains(name);
        }
        return query.count();
    }
    //根据商品访问量查询对应商品总数
    public long countByVisit(int min,int max){
        Datastore db= MorphiaPooUtil.getDb();
        Query<Product> query= db.createQuery(Product.class).filter("visitCount >=",min).filter("visitCount <",max);
//        long count = Integer.parseInt(String.valueOf(query.count()));
        long count = query.count();
        return count;
    }
    //根据商品访问量查询五个热点商品
    public List<Product> sortByVisit(){
        Datastore db=MorphiaPooUtil.getDb();
        Query<Product> query= db.createQuery(Product.class);
        FindOptions findOptions=new FindOptions().limit(5);
        List<Product> list=query.order("-visitCount").asList(findOptions);
        return list;
    }
    //修改
    public void update(Product product) {
        Datastore datastore = MorphiaPooUtil.getDb();
        Query<Product> query = datastore.createQuery(Product.class);
        query.filter("_id", product.getId());
        UpdateOperations<Product> update = datastore.createUpdateOperations(Product.class);
        if (product.getName() != null) {
            update.set("name", product.getName());
        }
        if (product.getPrice() != 0.0) {
            update.set("price", product.getPrice());
        }
        if (product.getSum() != null) {
            update.set("sum", product.getSum());
        }
        if (product.getVisitCount() != null) {
            update.set("visitCount", product.getVisitCount());
        }
        if (product.getAddDate() != null) {
            update.set("addDate", product.getAddDate());
        }
        if (product.getInfo() != null) {
            update.set("info", product.getInfo());
        }
        if (product.getStatus() == 0 || product.getStatus() == 1) {
            update.set("status", product.getStatus());
        }
        UpdateResults update1 = datastore.update(query, update);
        System.out.println(update1);
    }

    //5 分页查询
    public List<Product> addfindPage(int skip, int limit) {
        Datastore db = MorphiaPooUtil.getDb();
        Query<Product> query = db.createQuery(Product.class).field("sum").lessThan(100).order("sum");
        FindOptions findOptions = new FindOptions();
        findOptions.skip(skip).limit(limit);
        List<Product> products = query.asList(findOptions);
        return products;
    }

    //5 热销商品分页查询
    public List<Product> salefindPage(int skip, int limit) {
        Datastore db = MorphiaPooUtil.getDb();
        Query<Product> query = db.createQuery(Product.class).order("-visitCount");
        FindOptions findOptions = new FindOptions();
        findOptions.skip(skip).limit(limit);
        List<Product> products = query.asList(findOptions);
        return products;
    }

    //统计总数
    public long count() {
        Datastore db = MorphiaPooUtil.getDb();
        long count = db.getCount(Product.class);
        return count;
    }

    //待补货商品数量
    public Long addcount() {
        Datastore db = MorphiaPooUtil.getDb();

        Query<Product> products = db.find(Product.class, "sum <", 100);
        long count=products.count();
        return count;
    }
}
