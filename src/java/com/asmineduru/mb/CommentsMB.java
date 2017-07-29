package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Comment;
import com.asmineduru.util.MessagesController;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
public class CommentsMB implements Serializable {
    
    private final MainDao mainDao = new MainDao();
    
    private List<Comment> commentList;
    private Comment selectedComment;
    
    @PostConstruct
    public void init() {
        try {
            
            commentList = mainDao.findAllComments();
            
        } catch (Exception e) {
        }
    }
    
    public void pasiveComment() {
        try {
            
            selectedComment.setUsageStatus(false);
            mainDao.updateObject(selectedComment);
            MessagesController.bilgiVer("Yorum pasif yapılmıştır.");
        } catch (Exception e) {
            MessagesController.hataVer("Yorum pasif yapma işleminde hata oluştu");
        }
    }
    
    public void activeComment() {
        try {
            
            selectedComment.setUsageStatus(true);
            mainDao.updateObject(selectedComment);
            MessagesController.bilgiVer("Yorum aktif yapılmıştır.");
        } catch (Exception e) {
            MessagesController.hataVer("Yorum aktif etme işleminde hata oluştu");
        }
    }
    
    public void answerComment() {
        try {
            
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(new Date()); // sets calendar time/date
            cal.add(Calendar.HOUR_OF_DAY, 3); // adds one hour
            selectedComment.setAdminAnswerDate(cal.getTime());
            mainDao.updateObject(selectedComment);
            MessagesController.bilgiVer("Cevap kaydedilmiştir.");
        } catch (Exception e) {
            MessagesController.hataVer("Cevap kayıt işleminde hata oluştu");
        }
    }
        
    public List<Comment> getCommentList() {
        return commentList;
    }
    
    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
    
    public Comment getSelectedComment() {
        return selectedComment;
    }
    
    public void setSelectedComment(Comment selectedComment) {
        this.selectedComment = selectedComment;
    }
    
}
