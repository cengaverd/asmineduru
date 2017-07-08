package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Orders;
import com.asmineduru.util.MessagesController;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@ViewScoped
public class OrdersMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private List<Orders> orderList;
    
    @PostConstruct
    public void init() {
        try {

            orderList = mainDao.findOrders();

        } catch (Exception e) {
        }
    }
    
    public void saveCargoStatus(Orders order) {
        try {
            
            if (order!=null && order.getCargoStatus()!=null) {
                
                order.setCargoStatus(order.getCargoStatus()+" kargo takip numarası ile takip edebilirisiniz."); 
                mainDao.updateObject(order);
                MessagesController.bilgiVer("Kargo Durumu kaydedildi.");
            }
            
        } catch (Exception e) {
        }
    }

    public List<Orders> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Orders> orderList) {
        this.orderList = orderList;
    }

}
