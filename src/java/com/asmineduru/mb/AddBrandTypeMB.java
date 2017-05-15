package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Brand;
import com.asmineduru.model.Type;
import com.asmineduru.util.MessagesController;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@ViewScoped
public class AddBrandTypeMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private List<Brand> brandList;
    private Brand brand;
    private Brand selectedBrand;
    private Type type;

    private boolean showUpdate;

    @PostConstruct
    public void init() {
        try {

            brandList = mainDao.findAllBrand();

        } catch (Exception e) {
        }
    }

    public void saveBrand() {
        try {
            mainDao.saveBrandAndTypeList(selectedBrand);
            brandList = mainDao.findAllBrand();
            clearAddBrand();
            MessagesController.bilgiVer("Kaydetme işlemi yapıldı.");
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').hide();");

        } catch (Exception e) {

            MessagesController.hataVer("Kaydetme işleminde hata oluştu.");
        }
    }

    public void findBrandForUpdate() {
        try {

            showUpdate = true;

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').show();");

        } catch (Exception e) {

            MessagesController.hataVer("Güncelleme işleminde hata oluştu.");
        }
    }

    public void updateBrand() {
        try {

            mainDao.saveOrUpdateBrandAndTypeList(selectedBrand);
            brandList = mainDao.findAllBrand();
            clearAddBrand();
            MessagesController.bilgiVer("Marka bilgileri güncellenmiştir.");
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg').hide();");

        } catch (Exception e) {

            MessagesController.hataVer("Güncelleme işleminde hata oluştu.");
        }
    }

    public void deleteBrand() {
        try {
            mainDao.deleteBrandAndTypeList(selectedBrand);
            brandList = mainDao.findAllBrand();
            MessagesController.bilgiVer("Marka silinmiştir.");
        } catch (Exception e) {
            MessagesController.hataVer("Marka silme işleminde hata oluştu");
        }
    }

    public void clearAddBrand() {
        try {
            showUpdate = false;
            brand = new Brand();
            selectedBrand = new Brand();

        } catch (Exception e) {
            MessagesController.hataVer("Temizleme işleminde hata oluştu");
        }
    }

    public void clearAddType() {
        try {
            type = new Type();

        } catch (Exception e) {
            MessagesController.hataVer("Temizleme işleminde hata oluştu");
        }
    }

    public void addType() {
        try {
            type.setBrand(selectedBrand);
            selectedBrand.getTypeList().add(type);
            clearAddType();
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('addType').hide();");

        } catch (Exception e) {
            MessagesController.hataVer("Tip ekleme işleminde hata oluştu");
        }
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Brand getSelectedBrand() {
        return selectedBrand;
    }

    public void setSelectedBrand(Brand selectedBrand) {
        this.selectedBrand = selectedBrand;
    }

    public boolean isShowUpdate() {
        return showUpdate;
    }

    public void setShowUpdate(boolean showUpdate) {
        this.showUpdate = showUpdate;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
