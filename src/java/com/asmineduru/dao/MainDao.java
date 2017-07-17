package com.asmineduru.dao;

import com.asmineduru.model.Brand;
import com.asmineduru.model.Cart;
import com.asmineduru.model.Comment;
import com.asmineduru.model.Image;
import com.asmineduru.model.Likes;
import com.asmineduru.model.Member;
import com.asmineduru.model.OrderProduct;
import com.asmineduru.model.Orders;
import com.asmineduru.model.Product;
import com.asmineduru.model.Type;
import com.asmineduru.model.Users;
import com.asmineduru.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

/**
 *
 * @author Erhan
 */
public class MainDao extends Dao implements Serializable {

    public boolean loginControl(Users user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean loginAccept = false;
        try {
            List<Users> objs = session.createCriteria(Users.class)
                    .list();

            if ((objs != null) && (objs.size() > 0)) {

                for (Users veritabani : objs) {
                    loginAccept = veritabani.getUsername().equals(
                            user.getUsername())
                            && veritabani.getPassword().equals(
                                    user.getPassword());

                    if (loginAccept) {
                        return loginAccept;
                    }
                }
            }
        } catch (HibernateException e) {
            throw e;
        } finally {
            session.close();
        }
        return loginAccept;
    }

    public boolean loginControl(Member member) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean loginAccept = false;
        try {
            List<Member> objs = session.createCriteria(Member.class)
                    .list();

            if ((objs != null) && (objs.size() > 0)) {

                for (Member veritabani : objs) {
                    loginAccept = veritabani.getEmail().equals(
                            member.getEmail())
                            && veritabani.getPassword().equals(
                                    member.getPassword());

                    if (loginAccept) {
                        return loginAccept;
                    }
                }
            }
        } catch (HibernateException e) {
            throw e;
        } finally {
            session.close();
        }
        return loginAccept;
    }

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

    public List<Brand> findAllBrand() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Brand> brandList;
        try {

            brandList = session.getNamedQuery("Brand.findAll").list();

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

    public void saveProductAndImageList(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(product);
            for (Image image : product.getImageList()) {
                session.save(image);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void saveOrUpdateProductAndImageList(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(product);
            for (Image image : product.getImageList()) {
                session.saveOrUpdate(image);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void saveBrandAndTypeList(Brand brand) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(brand);
            for (Type type : brand.getTypeList()) {
                session.save(type);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void saveOrUpdateBrandAndTypeList(Brand brand) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(brand);
            for (Type type : brand.getTypeList()) {
                session.saveOrUpdate(type);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void deleteProductAndImageList(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(product);
            for (Image image : product.getImageList()) {
                session.delete(image);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void deleteBrandAndTypeList(Brand brand) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(brand);
            for (Type type : brand.getTypeList()) {
                session.delete(type);
            }
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

    public Type findTypeByTypeId(Integer typeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Type type;
        try {

            type = (Type) session.getNamedQuery("Type.findByTypeId").setParameter("typeId", typeId).uniqueResult();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return type;
    }

    public Product findProductByProductId(Integer productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Product product;
        try {

            product = (Product) session.getNamedQuery("Product.findByProductId").setParameter("productId", productId).uniqueResult();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return product;
    }

    public <T> T findObject(Class<T> c, String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        T result;
        try {
            result = c.cast(session.get(c, id));
        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    public List<Users> findAllUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Users> userList;
        try {

            String hql = "from Users";
            userList = session.createQuery(hql).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return userList;
    }

    public List<Member> findAllMembers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Member> memberList;
        try {

            String hql = "from Member";
            memberList = session.createQuery(hql).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return memberList;
    }
    
    public List<Comment> findAllComments() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Comment> commentList;
        try {

            String hql = "from Comment";
            commentList = session.createQuery(hql).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return commentList;
    }

    public Integer findProductMaxId() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Integer maxId;
        try {

            Criteria criteria = session
                    .createCriteria(Product.class)
                    .setProjection(Projections.max("productId"));
            maxId = (Integer) criteria.uniqueResult();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return maxId;
    }
    
    public Integer findOrderMaxId() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Integer maxId;
        try {

            Criteria criteria = session
                    .createCriteria(Orders.class)
                    .setProjection(Projections.max("orderId"));
            maxId = (Integer) criteria.uniqueResult();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return maxId;
    }

    public Member findMemberByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Member member;
        try {

            String hql = "from Member m where m.email=:email";
            member = (Member) session.createQuery(hql).setParameter("email", email).uniqueResult();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return member;
    }

    public Member findMemberByMemberIdAndToken(Integer memberId, String token) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Member member;
        try {

            String hql = "from Member m where m.memberId=:memberId and m.token=:token";
            member = (Member) session.createQuery(hql)
                    .setParameter("memberId", memberId)
                    .setParameter("token", token)
                    .uniqueResult();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return member;
    }

    public List<Product> findProductListOrderByMaxDiscountInUsage() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> productList;
        try {
            String hql = "FROM Product p WHERE p.usageStatus =1 order by p.discount desc";
            productList = session.createQuery(hql).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return productList;
    }

    public List<Cart> findCartListByMemberInUsage(Integer memberId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Cart> cartList;
        try {
            String hql = "FROM Cart c WHERE c.usageStatus =1 and c.member.memberId=:memberId";
            cartList = session.createQuery(hql)
                    .setParameter("memberId", memberId)
                    .list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return cartList;
    }

    public Likes findLikeByMemberAndProduct(Integer memberId, Integer productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Likes like;
        try {
            String hql = "FROM Likes l WHERE l.memberId=:memberId and l.productId=:productId";
            like = (Likes) session.createQuery(hql)
                    .setParameter("memberId", memberId)
                    .setParameter("productId", productId)
                    .uniqueResult();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return like;
    }
    
    public List<Likes> findLikeByMember(Integer memberId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Likes> likes;
        try {
            String hql = "FROM Likes l WHERE l.memberId=:memberId";
            likes = session.createQuery(hql)
                    .setParameter("memberId", memberId).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return likes;
    }
    
    public List<Comment> findCommentListByProduct(Integer productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Comment> commentList;
        try {
            String hql = "FROM Comment c WHERE c.productId=:productId and c.usageStatus=1 order by c.commentId desc";
            commentList = session.createQuery(hql)
                    .setParameter("productId", productId).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return commentList;
    }

    public void saveOrUpdateOrderList(Orders order) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.saveOrUpdate(order);
            for (OrderProduct orderProduct : order.getOrderProducts()) {
                session.saveOrUpdate(orderProduct);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
    
     public void updateCartList(List<Cart> cartList) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            for (Cart cart : cartList) {
                session.update(cart);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
     
     public List<Orders> findOrders() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Orders> orders;
        try {
            String hql = "from Orders";
            orders = session.createQuery(hql).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return orders;
    }
     
     public List<Orders> findMemberOrderList(Integer memberId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Orders> orders;
        try {
            String hql = "from Orders o where o.member.memberId=:memberId order by o.orderId desc, o.usageStatus desc";
            orders = session.createQuery(hql).setParameter("memberId", memberId).list();

        } catch (Exception e) {
            throw e;
        } finally {
            session.close();
        }
        return orders;
    }
}
