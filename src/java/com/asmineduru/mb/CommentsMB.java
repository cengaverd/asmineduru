package com.asmineduru.mb;

import com.asmineduru.dao.MainDao;
import com.asmineduru.model.Comment;
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
    
    public void deleteComment() {
        try {
            
            selectedComment.setUsageStatus(false);
            mainDao.updateObject(selectedComment);
            MessagesController.bilgiVer("Yorum pasif yapılmıştır.");
        } catch (Exception e) {
            MessagesController.hataVer("Yorum pasif yapma işleminde hata oluştu");
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
