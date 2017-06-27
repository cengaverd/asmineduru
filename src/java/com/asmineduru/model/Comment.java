package com.asmineduru.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(catalog = "asmine_main", schema = "")
@XmlRootElement
public class Comment implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    @Column(length = 500)
    private String comment;    
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer productId;
    @OneToOne
    @JoinColumn(name = "member")
    private Member member;    
    @Column(name = "commentDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentDate; 
    @Column(length = 500)
    private String adminAnswer;
    @Column(name = "adminAnswerDate", columnDefinition="DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date adminAnswerDate;     
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean adminReadStatus;
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean memberReadStatus;
    @Basic(optional = false)
    @Column(nullable = false)
    private boolean usageStatus;
    
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getAdminAnswer() {
        return adminAnswer;
    }

    public void setAdminAnswer(String adminAnswer) {
        this.adminAnswer = adminAnswer;
    }

    public Date getAdminAnswerDate() {
        return adminAnswerDate;
    }

    public void setAdminAnswerDate(Date adminAnswerDate) {
        this.adminAnswerDate = adminAnswerDate;
    }

    public boolean isAdminReadStatus() {
        return adminReadStatus;
    }

    public void setAdminReadStatus(boolean adminReadStatus) {
        this.adminReadStatus = adminReadStatus;
    }

    public boolean isMemberReadStatus() {
        return memberReadStatus;
    }

    public void setMemberReadStatus(boolean memberReadStatus) {
        this.memberReadStatus = memberReadStatus;
    }

    public boolean isUsageStatus() {
        return usageStatus;
    }

    public void setUsageStatus(boolean usageStatus) {
        this.usageStatus = usageStatus;
    }    
}
