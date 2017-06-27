package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Cart;
import com.asmineduru.model.Comment;
import com.asmineduru.model.Likes;
import com.asmineduru.model.Product;
import com.asmineduru.model.Type;
import com.asmineduru.util.MessagesController;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@SessionScoped
public class ProductMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    @ManagedProperty(value = "#{memberSessionMB}")
    private MemberSessionMB memberSessionMB;

    private Product selectedProduct;
    private Integer productId;
    private String contourChart;
    private BigInteger quantity;

    private Cart newCart;
    private List<Cart> cartList;
    private Cart selectedCart;
    private BigInteger totalPrice;

    private List<Product> productList;
    private Type selectedType;
    private Integer typeId;

    private List<Comment> commentList;
    private Comment comment;
    private String inputComment;
    private boolean adminAnswer;

    @PostConstruct
    public void init() {
        try {

            productList = new ArrayList<>();
            findCartList();

        } catch (Exception e) {
        }
    }

    public void findCartList() {
        try {

            totalPrice = BigInteger.ZERO;

            if (memberSessionMB.getMember() != null) {
                cartList = mainDao.findCartListByMemberInUsage(memberSessionMB.getMember().getMemberId());

                if (cartList != null && !cartList.isEmpty()) {
                    for (Cart cart : cartList) {
                        totalPrice = totalPrice.add(cart.getProduct().getProductPrice().multiply(cart.getQuantity()));
                    }
                }
            }

        } catch (Exception e) {
        }
    }

    public String goProducts(Type type) {
        try {

            selectedType = type;
            productList = mainDao.findProductByTypeIdInUsage(selectedType.getTypeId());
            if (memberSessionMB.getMember() != null) {
                List<Likes> likes = mainDao.findLikeByMember(memberSessionMB.getMember().getMemberId());

                if (likes != null && !likes.isEmpty()) {

                    if (productList != null && !productList.isEmpty()) {

                        for (Product product : productList) {

                            for (Likes like : likes) {

                                if (product.getProductId().equals(like.getProductId())) {

                                    product.setMemberFavorite(true);

                                }
                            }
                        }
                    }
                }
            }
            return NavigationBean.redirectToProducts();

        } catch (Exception e) {

        }
        return NavigationBean.redirectToProducts();
    }

    public void loadData(Product product) {
        try {

            selectedProduct = product;

            quantity = BigInteger.ONE;

            StringBuilder sb = new StringBuilder();
            sb.append("data:image/png;base64,");
            sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(selectedProduct.getImageList().get(0).getImage(), false)));
            contourChart = sb.toString();

            comment = new Comment();
            commentList = new ArrayList<>();
            commentList = mainDao.findCommentListByProduct(selectedProduct.getProductId());
            adminAnswer = false;

        } catch (Exception e) {

        }
    }

    public String addComment() {
        try {

            if (memberSessionMB.getMember() != null) {

                if (inputComment != null && !inputComment.equals("")) {

                    if (adminAnswer) {

                        comment.setAdminAnswer(inputComment);
                        comment.setAdminAnswerDate(new Date());
                        mainDao.updateObject(comment);
                        commentList = mainDao.findCommentListByProduct(selectedProduct.getProductId());
                        comment = new Comment();
                        inputComment=null;
                    } else {

                        comment.setComment(inputComment);
                        comment.setMember(memberSessionMB.getMember());
                        comment.setProductId(selectedProduct.getProductId());
                        comment.setUsageStatus(true);
                        comment.setCommentDate(new Date());
                        mainDao.saveObject(comment);
                        commentList = mainDao.findCommentListByProduct(selectedProduct.getProductId());
                        comment = new Comment();
                        inputComment=null;
                    }
                } else {
                    MessagesController.uyariVer("Yorum girmelisiniz.");
                }

            } else {
                return NavigationBean.redirectToMemberLogin();
            }

        } catch (Exception e) {

        }
        return null;
    }

    public void adminAnswer(Comment comment) {
        try {

            adminAnswer = true;
            this.comment = comment;

        } catch (Exception e) {

        }
    }

    public String goCart() {

        try {
            totalPrice = BigInteger.ZERO;

            if (memberSessionMB.getMember() != null) {

                boolean productAdd = false;

                cartList = mainDao.findCartListByMemberInUsage(memberSessionMB.getMember().getMemberId());

                if (cartList != null && !cartList.isEmpty()) {
                    for (Cart cart : cartList) {
                        if (cart.getProduct().getProductId().equals(selectedProduct.getProductId())) {

                            cart.setQuantity(quantity);

                            mainDao.updateObject(cart);
                            productAdd = true;
                            break;

                        }
                    }
                }

                if (!productAdd) {

                    newCart = new Cart();
                    newCart.setProduct(selectedProduct);
                    newCart.setMember(memberSessionMB.getMember());

                    if (quantity != null) {
                        newCart.setQuantity(quantity);
                    } else {
                        newCart.setQuantity(BigInteger.ONE);
                    }

                    newCart.setUsageStatus(true);

                    mainDao.saveObject(newCart);

                }

                cartList = mainDao.findCartListByMemberInUsage(memberSessionMB.getMember().getMemberId());

                if (cartList != null && !cartList.isEmpty()) {
                    for (Cart cart : cartList) {
                        totalPrice = totalPrice.add(cart.getProduct().getProductPrice().multiply(cart.getQuantity()));
                    }
                }

                return "cart.xhtml";
            } else {

                return "login.xhtml";
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void delete() {
        try {

            totalPrice = BigInteger.ZERO;

            selectedCart.setUsageStatus(false);
            mainDao.updateObject(selectedCart);

            cartList = mainDao.findCartListByMemberInUsage(memberSessionMB.getMember().getMemberId());

            if (cartList != null && !cartList.isEmpty()) {
                for (Cart cart : cartList) {
                    totalPrice = totalPrice.add(cart.getProduct().getProductPrice().multiply(cart.getQuantity()));
                }
            }

        } catch (Exception e) {
        }

    }

    public void decrease(Cart cart) {
        try {
            if (cart.getQuantity().intValue() > 1) {

                totalPrice = BigInteger.ZERO;

                cart.setQuantity(cart.getQuantity().subtract(BigInteger.ONE));
                mainDao.updateObject(cart);

                if (cartList != null && !cartList.isEmpty()) {
                    for (Cart crt : cartList) {
                        totalPrice = totalPrice.add(crt.getProduct().getProductPrice().multiply(crt.getQuantity()));
                    }
                }
            }

        } catch (Exception e) {
        }

    }

    public void increase(Cart cart) {
        try {
            totalPrice = BigInteger.ZERO;
            cart.setQuantity(cart.getQuantity().add(BigInteger.ONE));
            mainDao.updateObject(cart);
            if (cartList != null && !cartList.isEmpty()) {
                for (Cart crt : cartList) {
                    totalPrice = totalPrice.add(crt.getProduct().getProductPrice().multiply(crt.getQuantity()));
                }
            }

        } catch (Exception e) {
        }

    }

    public String like(Product product) {
        try {
            if (memberSessionMB.getMember() != null) {

                Likes like = mainDao.findLikeByMemberAndProduct(memberSessionMB.getMember().getMemberId(), product.getProductId());

                if (like == null) {
                    like = new Likes();
                    like.setProductId(product.getProductId());
                    like.setMemberId(memberSessionMB.getMember().getMemberId());
                    mainDao.saveObject(like);
                    product.setLikeCount(product.getLikeCount() + 1);
                    product.setMemberFavorite(true);
                    MessagesController.bilgiVer("Ürün favorilerinize eklendi.");
                }

            } else {
                return NavigationBean.redirectToMemberLogin();
            }

        } catch (Exception e) {
        }
        return null;
    }

    public String disLike(Product product) {
        try {
            if (memberSessionMB.getMember() != null) {

                Likes like = mainDao.findLikeByMemberAndProduct(memberSessionMB.getMember().getMemberId(), product.getProductId());

                if (like != null) {

                    mainDao.deleteObject(like);
                    product.setLikeCount(product.getLikeCount() - 1);
                    product.setMemberFavorite(false);
                    MessagesController.bilgiVer("Ürün favorilerinizden çıkarıldı.");
                }

            } else {
                return NavigationBean.redirectToMemberLogin();
            }

        } catch (Exception e) {
        }
        return null;
    }

///////////////////// Getter ve Setter ////////////////////////////////////////
    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getContourChart() {
        return contourChart;
    }

    public void setContourChart(String contourChart) {
        this.contourChart = contourChart;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }

    public MemberSessionMB getMemberSessionMB() {
        return memberSessionMB;
    }

    public void setMemberSessionMB(MemberSessionMB memberSessionMB) {
        this.memberSessionMB = memberSessionMB;
    }

    public Cart getNewCart() {
        return newCart;
    }

    public void setNewCart(Cart newCart) {
        this.newCart = newCart;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public Cart getSelectedCart() {
        return selectedCart;
    }

    public void setSelectedCart(Cart selectedCart) {
        this.selectedCart = selectedCart;
    }

    public BigInteger getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigInteger totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Type getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(Type selectedType) {
        this.selectedType = selectedType;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getInputComment() {
        return inputComment;
    }

    public void setInputComment(String inputComment) {
        this.inputComment = inputComment;
    }

    public boolean isAdminAnswer() {
        return adminAnswer;
    }

    public void setAdminAnswer(boolean adminAnswer) {
        this.adminAnswer = adminAnswer;
    }

}
