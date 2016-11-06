/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rollsoftware.br.accountmanager.db.entity;

import com.rollsoftware.br.util.CypherUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Rogério
 */
//@MappedSuperclass
@Entity
@Table(name = "LOGIN_INFO",
        schema = "ACCOUNT_MANAGER_DB_APP",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"LIUSER"})
        }
)
@DiscriminatorValue("LoginInfo")
@PrimaryKeyJoinColumn(name = "LIHASHFK", referencedColumnName = "ODHASHPK")
@XmlRootElement(name = "login")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "user", "pass",
    "firstName", "lastName",
    "dateCreated", "dateAccessed", "dateActivated", "dateExpired",
    "successCount", "blockedCount", "errorCount",
    "dateBlocked", "dateSoftban", "datePermBan",
    "tokenInfos"
})
public class LoginInfo extends ObjectData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "LIUSER",
            unique = true, nullable = false, length = 128)
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

    @OneToMany(
            mappedBy = "loginInfo",
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(name = "TIHASHPK", referencedColumnName = "ODHASHPK")
    @OrderBy("dateCreated ASC")
    @XmlElement(name = "token")
    @XmlIDREF
    private List<TokenInfo> tokenInfos;

    public LoginInfo() {
        this("");
    }

    public LoginInfo(String hash) {
        this(0, "LoginInfo", hash,
                "", "", "", "",
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime());
    }

    public LoginInfo(Integer id, String type, String hash,
            String user, String pass, String firstName, String lastName,
            Date dateCreated, Date dateAccessed) {
        this(id, type, hash,
                user, pass, firstName, lastName,
                0, 0, 0,
                null, null,
                dateCreated, dateAccessed,
                null, null, null,
                new ArrayList());
    }

    public LoginInfo(Integer id, String type, String hash,
            String user, String pass, String firstName, String lastName,
            Integer successCount, Integer errorCount, Integer blockedCount,
            Date dateSoftban, Date datePermBan,
            Date dateCreated, Date dateAccessed, Date dateActivated,
            Date dateExpired, Date dateBlocked,
            List<TokenInfo> tokenInfos) {
        super(id, type, hash);
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

    @Override
    public void generateHash() {
        String _hash = CypherUtils.generateHash(
                user, firstName, lastName
        );
        setHash(_hash);
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

    public void encryptPass() {
        pass = CypherUtils.encrypt(
                getHash(), getHash(), pass);
    }

    public void decryptPass() {
        pass = CypherUtils.decrypt(
                getHash(), getHash(), pass);
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

    public List<TokenInfo> getTokenInfos() {
        return tokenInfos;
    }

    public void setTokenInfos(List<TokenInfo> tokenInfos) {
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
        return "LoginInfo[id=" + getId()
                + ", hash=" + getHash()
                + ", user=" + getUser() + "]";
    }
}
