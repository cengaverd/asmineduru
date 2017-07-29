package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Brand;
import com.asmineduru.model.Comment;
import com.asmineduru.model.Likes;
import com.asmineduru.model.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/**
 * @author ERHAN
 *
 */
@ManagedBean
@RequestScoped
public class CommentMB implements Serializable {

    private final MainDao mainDao = new MainDao();

    @ManagedProperty(value = "#{memberSessionMB}")
    private MemberSessionMB memberSessionMB;

    private List<Comment> commentList;

    @PostConstruct
    public void init() {
        try {
            
            commentList = new ArrayList<>();

            if (memberSessionMB.getMember() != null) {
                commentList = mainDao.findCommentByMember(memberSessionMB.getMember().getMemberId());
            }

        } catch (Exception e) {
        }
    }
    
    public void deleteComment( ) {
        try {
           
        } catch (Exception e) {
        }
    }

///////////////////// Getter ve Setter ////////////////////////////////////////

    public MemberSessionMB getMemberSessionMB() {
        return memberSessionMB;
    }

    public void setMemberSessionMB(MemberSessionMB memberSessionMB) {
        this.memberSessionMB = memberSessionMB;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    } 
}
