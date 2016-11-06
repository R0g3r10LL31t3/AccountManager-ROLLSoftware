/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rollsoftware.br.accountmanager.db.entity;

import com.rollsoftware.br.util.CypherUtils;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TOKEN_INFO", schema = "ACCOUNT_MANAGER_DB_APP",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"TIACCESSTOKEN"})
        }
)
@DiscriminatorValue("TokenInfo")
@PrimaryKeyJoinColumn(name = "TIHASHFK", referencedColumnName = "ODHASHPK")
@XmlRootElement(name = "token")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "accessToken", "userIP",
    "dateCreated", "dateAccessed", "dateExpires",
    "successCount", "refusedCount", "errorCount",
    "loginInfo"
})
public class TokenInfo extends ObjectData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "TIACCESSTOKEN",
            unique = true, nullable = false, length = 128)
    private String accessToken;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "TIUSERIP", nullable = false, length = 32)
    private String userIP;

    @Column(name = "TISUCCESSCOUNT")
    private Integer successCount;

    @Column(name = "TIREFUSEDCOUNT")
    private Integer refusedCount;

    @Column(name = "TIERRORCOUNT")
    private Integer errorCount;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TIDATECREATED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TIDATEACCESSED", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAccessed;

    @Basic(optional = false)
    @NotNull
    @Column(name = "TIDATEEXPIRES", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExpires;

    @NotNull
    @ManyToOne(
            optional = false,
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "TI_LOGININFO_HASHFK",
            referencedColumnName = "LIHASHFK",
            nullable = false
    )
    @XmlIDREF
    @XmlElement(name = "login")
    private LoginInfo loginInfo;

    public TokenInfo() {
        this("");
    }

    public TokenInfo(String hash) {
        this(0, "TokenInfo", hash,
                "", "",
                0, 0, 0,
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime(),
                null, null);
    }

    public TokenInfo(Integer id, String type, String hash,
            String accessToken, String userIP,
            Integer successCount, Integer refusedCount, Integer errorCount,
            Date dateCreated, Date dateAccessed, Date dateExpires,
            LoginInfo loginInfo) {
        super(id, type, hash);
        this.accessToken = accessToken;
        this.userIP = userIP;
        this.successCount = successCount;
        this.refusedCount = refusedCount;
        this.errorCount = errorCount;
        this.dateCreated = dateCreated;
        this.dateAccessed = dateAccessed;
        this.dateExpires = dateExpires;
        this.loginInfo = loginInfo;
    }

    @Override
    public void generateHash() {
        String _hash = CypherUtils.generateHash(
                loginInfo.getHash(), accessToken, userIP
        );
        setHash(_hash);
    }

    public void generateToken() {
        String _hash = CypherUtils.generateHash(
                loginInfo.getHash(), accessToken, userIP
        );
        setAccessToken(_hash);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getRefusedCount() {
        return refusedCount;
    }

    public void setRefusedCount(Integer refusedCount) {
        this.refusedCount = refusedCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
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

    public Date getDateExpires() {
        return dateExpires;
    }

    public void setDateExpires(Date dateExpires) {
        this.dateExpires = dateExpires;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work
        // in the case the id fields are not set
        if (!(object instanceof TokenInfo)) {
            return false;
        }
        TokenInfo other = (TokenInfo) object;
        if ((this.getId() == null && other.getId() != null)
                || (this.getId() != null
                && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TokenInfo[id=" + getId()
                + ", hash=" + getHash()
                + ", token=" + getAccessToken() + "]";
    }
}
