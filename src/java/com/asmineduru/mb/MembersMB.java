package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Brand;
import com.asmineduru.model.Member;
import com.asmineduru.model.Type;
import com.asmineduru.model.Users;
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
public class MembersMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    private List<Member> memberList;
    private Member selectedMember;


    @PostConstruct
    public void init() {
        try {

            memberList = mainDao.findAllMembers();

        } catch (Exception e) {
        }
    }
    
    public void deleteMember() {
        try {
            mainDao.deleteObject(selectedMember);
            memberList = mainDao.findAllMembers();
            MessagesController.bilgiVer("Üye silinmiştir.");
        } catch (Exception e) {
            MessagesController.hataVer("Üye silme işleminde hata oluştu");
        }
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public Member getSelectedMember() {
        return selectedMember;
    }

    public void setSelectedMember(Member selectedMember) {
        this.selectedMember = selectedMember;
    }
    

}
