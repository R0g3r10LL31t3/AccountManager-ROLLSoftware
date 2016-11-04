/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rollsoftware.br.accountmanager.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rogério
 */
//@MappedSuperclass
@Entity
@Table(name = "LOGIN_INFO",
        schema = "ACCOUNT_MANAGER_DB_APP",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"LIIDFK"})
            ,@UniqueConstraint(columnNames = {"LIUSER"})
        }
)
@DiscriminatorValue("LoginInfo")
@PrimaryKeyJoinColumn(name = "LIIDFK", referencedColumnName = "ODIDPK")
@XmlRootElement
public class LoginInfo extends ObjectData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LIIDFK", nullable = false)
    private Integer idFK;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "LIUSER", nullable = false, length = 128)
    private String user;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "LIPASS", nullable = false, length = 128)
    private String pass;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "LIFIRSTNAME", nullable = false, length = 64)
    private String firstName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "LILASTNAME", nullable = false, length = 256)
    private String lastName;

    @Column(name = "LISUCCESSCOUNT")
    private Integer successCount;

    @Column(name = "LIERRORCOUNT")
    private Integer errorCount;

    @Column(name = "LIBLOCKEDCOUNT")
    private Integer blockedCount;

    @Column(name = "LIDATESOFTBAN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSoftban;

    @Column(name = "LIDATEPERMBAN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePermBan;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LIDATECREATED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Basic(optional = false)
    @NotNull
    @Column(name = "LIDATEACCESSED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAccessed;

    @Column(name = "LIDATEACTIVATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateActivated;

    @Column(name = "LIDATEEXPIRED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExpired;

    @Column(name = "LIDATEBLOCKED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateBlocked;

    @OneToMany(mappedBy = "loginInfo")
    @JoinColumn(name = "TI_LOGIN_INFO_FK")
    @OrderBy("dateCreated ASC")
    private Collection<TokenInfo> tokenInfos;

    public LoginInfo() {
        this(0);
    }

    public LoginInfo(Integer idFK) {
        this(idFK, "LoginInfo", "", "", "", "", "",
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime());
    }

    public LoginInfo(Integer idFK, String type, String hash,
            String user, String pass, String firstName, String lastName,
            Date dateCreated, Date dateAccessed) {
        this(idFK, type, hash,
                user, pass, firstName, lastName,
                0, 0, 0,
                null, null,
                dateCreated, dateAccessed,
                null, null, null,
                new ArrayList());
    }

    public LoginInfo(Integer idFK, String type, String hash,
            String user, String pass, String firstName, String lastName,
            Integer successCount, Integer errorCount, Integer blockedCount,
            Date dateSoftban, Date datePermBan,
            Date dateCreated, Date dateAccessed, Date dateActivated,
            Date dateExpired, Date dateBlocked,
            Collection<TokenInfo> tokenInfos) {
        super(idFK, type, hash);
        this.idFK = idFK;
        this.user = user;
        this.pass = pass;
        this.firstName = firstName;
        this.lastName = lastName;
        this.successCount = successCount;
        this.errorCount = errorCount;
        this.blockedCount = blockedCount;
        this.dateSoftban = dateSoftban;
        this.datePermBan = datePermBan;
        this.dateCreated = dateCreated;
        this.dateAccessed = dateAccessed;
        this.dateActivated = dateActivated;
        this.dateExpired = dateExpired;
        this.dateBlocked = dateBlocked;
        this.tokenInfos = tokenInfos;
    }

    public Integer getIdFK() {
        return getId();
    }

    public void setIdFK(Integer idFK) {
        setId(idFK);
        this.idFK = idFK;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void encrypt() {
    }

    public void decrypt() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    public Integer getBlockedCount() {
        return blockedCount;
    }

    public void setBlockedCount(Integer blockedCount) {
        this.blockedCount = blockedCount;
    }

    public Date getDateSoftban() {
        return dateSoftban;
    }

    public void setDateSoftban(Date dateSoftban) {
        this.dateSoftban = dateSoftban;
    }

    public Date getDatePermBan() {
        return datePermBan;
    }

    public void setDatePermBan(Date datePermBan) {
        this.datePermBan = datePermBan;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateAccessed() {
        return dateAccessed;
    }

    public void setDateAccessed(Date dateAccessed) {
        this.dateAccessed = dateAccessed;
    }

    public Date getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(Date dateActivated) {
        this.dateActivated = dateActivated;
    }

    public Date getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(Date dateExpired) {
        this.dateExpired = dateExpired;
    }

    public Date getDateBlocked() {
        return dateBlocked;
    }

    public void setDateBlocked(Date dateBlocked) {
        this.dateBlocked = dateBlocked;
    }

    public Collection<TokenInfo> getTokenInfos() {
        return tokenInfos;
    }

    public void setTokenInfos(Collection<TokenInfo> tokenInfos) {
        this.tokenInfos = tokenInfos;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in
        // the case the id fields are not set
        if (!(object instanceof LoginInfo)) {
            return false;
        }
        LoginInfo other = (LoginInfo) object;
        if ((this.getId() == null && other.getId() != null)
                || (this.getId() != null
                && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LoginInfo[idFK=" + getId() + ", user=" + getUser() + "]";
    }

}
