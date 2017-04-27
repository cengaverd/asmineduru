package com.asmineduru.dao;

import com.asmineduru.model.Brand;
import com.asmineduru.model.Image;
import com.asmineduru.model.Product;
import com.asmineduru.model.Type;
import com.asmineduru.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Erhan
 */
public class MainDao extends Dao implements Serializable {

    public List<Brand> findAllBrandInUsage() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Brand> brandList;
        try {

            brandList = session.getNamedQuery("Brand.findByUsageStatus").setInteger("usageStatus", 1).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return brandList;
    }

    public List<Product> findAllProduct() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList;
        try {

            productList = session.getNamedQuery("Product.findAll").list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return productList;
    }

    public List<Type> findAllTypeByBrand(Brand brand) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Type> typeList;
        try {

            typeList = session.getNamedQuery("Type.findByBrand").setParameter("brand", brand).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return typeList;
    }

    public Image findImageByImageId(Integer imageId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Image image;
        try {

            image = (Image) session.getNamedQuery("Image.findByImageId").setParameter("imageId", imageId).uniqueResult();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return image;
    }

    public List<Product> findLeftSideProductInUsage() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList;
        try {
            String hql = "FROM Product p WHERE p.usageStatus =1 and p.showLeftSide=1";
            productList = session.createQuery(hql).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return productList;
    }
    
    public List<Product> findMainPageProductInUsage() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList;
        try {
            String hql = "FROM Product p WHERE p.usageStatus =1 and p.showMainPage=1";
            productList = session.createQuery(hql).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return productList;
    }
    
    public void saveProductAndImage(Product product, Image image) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(product);
            session.save(image);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    public void saveOrUpdateProductAndImage(Product product, Image image) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.saveOrUpdate(image);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
    public List<Product> findProductByTypeIdInUsage(Integer typeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList;
        try {
            String hql = "FROM Product p WHERE p.usageStatus =1 and p.type.typeId=:typeId";
            productList = session.createQuery(hql).setParameter("typeId", typeId).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return productList;
    }
}
