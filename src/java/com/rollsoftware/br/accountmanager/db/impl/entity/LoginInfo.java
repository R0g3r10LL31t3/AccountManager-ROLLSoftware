/*
 *          Copyright 2016-2026 Rogério Lecarião Leite
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  CEO 2016: Rogério Lecarião Leite; ROLL Software
 */
package com.rollsoftware.br.accountmanager.db.impl.entity;

import com.rollsoftware.br.common.db.entity.ObjectData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
@Entity
@Table(name = "LOGIN_INFO",
        schema = "ACCOUNT_MANAGER_DB_APP",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"LIUSER"})
        }
)
@DiscriminatorValue("LoginInfo")
@PrimaryKeyJoinColumn(name = "LIUUIDFK", referencedColumnName = "OBJ_UUID_PK")
@XmlRootElement(name = "login")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "login", propOrder = {
    "credentialInfo", "userInfo",
    "dateCreated", "dateAccessed", "dateActivated", "dateExpired",
    "successCount", "blockedCount", "errorCount",
    "dateBlocked", "dateSoftban", "datePermBan",
    "tokenInfos"
})
public class LoginInfo extends ObjectData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    @Basic(optional = false)
    @NotNull
    @Column(name = "LIVERSION", nullable = false)
    @XmlTransient
    private Integer liVersion;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "user",
                column = @Column(
                        name = "LIUSER", nullable = false, length = 256))
        ,@AttributeOverride(name = "pass",
                column = @Column(
                        name = "LIPASS", nullable = false, length = 256))
    })
    @XmlElement(name = "credential")
    private CredentialInfo credentialInfo;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "firstName",
                column = @Column(
                        name = "LIFIRSTNAME", nullable = false, length = 256))
        ,@AttributeOverride(name = "lastName",
                column = @Column(
                        name = "LILASTNAME", nullable = false, length = 256))
    })
    @XmlElement(name = "user")
    private UserInfo userInfo;

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
    @JoinColumn(name = "TIUUIDPK", referencedColumnName = "OBJ_UUID_PK")
    @OrderBy("dateCreated ASC")
    @XmlElement(name = "token")
    @XmlIDREF
    private List<TokenInfo> tokenInfos;

    public LoginInfo() {
        this("");
    }

    public LoginInfo(String uuid) {
        this(0, "LoginInfo", uuid,
                new CredentialInfo(), new UserInfo(),
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime());
    }

    public LoginInfo(Integer id, String type, String uuid,
            CredentialInfo credentialInfo, UserInfo userInfo,
            Date dateCreated, Date dateAccessed) {
        this(id, type, uuid,
                credentialInfo, userInfo,
                0, 0, 0,
                null, null,
                dateCreated, dateAccessed,
                null, null, null,
                new ArrayList());
    }

    public LoginInfo(Integer id, String type, String uuid,
            CredentialInfo credentialInfo, UserInfo userInfo,
            Integer successCount, Integer errorCount, Integer blockedCount,
            Date dateSoftban, Date datePermBan,
            Date dateCreated, Date dateAccessed, Date dateActivated,
            Date dateExpired, Date dateBlocked,
            List<TokenInfo> tokenInfos) {
        this(id, type, uuid,
                credentialInfo, userInfo,
                successCount, errorCount, blockedCount,
                dateSoftban, datePermBan,
                dateCreated, dateAccessed, dateActivated,
                dateExpired, dateBlocked,
                tokenInfos, 0);
    }

    public LoginInfo(Integer id, String type, String uuid,
            CredentialInfo credentialInfo, UserInfo userInfo,
            Integer successCount, Integer errorCount, Integer blockedCount,
            Date dateSoftban, Date datePermBan,
            Date dateCreated, Date dateAccessed, Date dateActivated,
            Date dateExpired, Date dateBlocked,
            List<TokenInfo> tokenInfos,
            Integer liVersion) {
        super(id, type, uuid);

        this.credentialInfo = credentialInfo;
        this.userInfo = userInfo;
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
        this.liVersion = liVersion;

        init();
    }

    private void init() {
        credentialInfo.setLoginInfo(this);
        userInfo.setLoginInfo(this);
    }

    public CredentialInfo getCredentialInfo() {
        return credentialInfo;
    }

    public void setCredentialInfo(CredentialInfo credentialInfo) {
        Objects.requireNonNull(credentialInfo);

        this.credentialInfo = credentialInfo;
        if (credentialInfo.getLoginInfo() != this) {
            credentialInfo.setLoginInfo(this);
        }
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        Objects.requireNonNull(userInfo);

        this.userInfo = userInfo;
        if (userInfo.getLoginInfo() != this) {
            userInfo.setLoginInfo(this);
        }
    }

    @Override
    public void generateUUID() {
        super.generateUUID();
        encryptPass();
    }

    public String getUser() {
        return credentialInfo.getUser();
    }

    public void setUser(String user) {
        credentialInfo.setUser(user);
    }

    public String getPass() {
        return credentialInfo.getPass();
    }

    public void setPass(String pass) {
        credentialInfo.setPass(pass);
    }

    public void encryptPass() {
        credentialInfo.encryptPass();
    }

    public void decryptPass() {
        credentialInfo.decryptPass();
    }

    public String getFirstName() {
        return userInfo.getFirstName();
    }

    public void setFirstName(String firstName) {
        userInfo.setFirstName(firstName);
    }

    public String getLastName() {
        return userInfo.getLastName();
    }

    public void setLastName(String lastName) {
        userInfo.setLastName(lastName);
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
        int _hash = super.hashCode();
        _hash += (getCredentialInfo() != null ? getCredentialInfo().hashCode() : 0);
        _hash += (getUserInfo() != null ? getUserInfo().hashCode() : 0);
        return _hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in
        // the case the id fields are not set
        if (!(object instanceof LoginInfo)) {
            return false;
        }
        return super.equals(object);
    }

    @Override
    public String toString() {
        return "LoginInfo[id=" + getId()
                + ", uuid=" + getUUID()
                + ",\ncredential={\n\t" + getCredentialInfo() + "\n}"
                + ",\nuser={\n\t" + getUserInfo() + "\n}]";
    }
}
