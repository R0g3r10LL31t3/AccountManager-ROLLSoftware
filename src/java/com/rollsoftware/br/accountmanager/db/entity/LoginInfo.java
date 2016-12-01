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
package com.rollsoftware.br.accountmanager.db.entity;

import com.rollsoftware.br.util.CypherUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
//@MappedSuperclass
@Entity
@Table(name = "LOGIN_INFO",
        schema = "ACCOUNT_MANAGER_DB_APP",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"LIUSER"})
        }
)
@DiscriminatorValue("LoginInfo")
@PrimaryKeyJoinColumn(name = "LIUUIDFK", referencedColumnName = "ODUUIDPK")
@XmlRootElement(name = "login")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "login", propOrder = {
    "user", "pass",
    "firstName", "lastName",
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
    @JoinColumn(name = "TIUUIDPK", referencedColumnName = "ODUUIDPK")
    @OrderBy("dateCreated ASC")
    @XmlElement(name = "token")
    @XmlIDREF
    private List<TokenInfo> tokenInfos;

    public LoginInfo() {
        this("");
    }

    public LoginInfo(String uuid) {
        this(0, "LoginInfo", uuid,
                "", "", "", "",
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime());
    }

    public LoginInfo(Integer id, String type, String uuid,
            String user, String pass, String firstName, String lastName,
            Date dateCreated, Date dateAccessed) {
        this(id, type, uuid,
                user, pass, firstName, lastName,
                0, 0, 0,
                null, null,
                dateCreated, dateAccessed,
                null, null, null,
                new ArrayList());
    }

    public LoginInfo(Integer id, String type, String uuid,
            String user, String pass, String firstName, String lastName,
            Integer successCount, Integer errorCount, Integer blockedCount,
            Date dateSoftban, Date datePermBan,
            Date dateCreated, Date dateAccessed, Date dateActivated,
            Date dateExpired, Date dateBlocked,
            List<TokenInfo> tokenInfos) {
        this(id, type, uuid,
                user, pass, firstName, lastName,
                successCount, errorCount, blockedCount,
                dateSoftban, datePermBan,
                dateCreated, dateAccessed, dateActivated,
                dateExpired, dateBlocked,
                tokenInfos, 0);
    }

    public LoginInfo(Integer id, String type, String uuid,
            String user, String pass, String firstName, String lastName,
            Integer successCount, Integer errorCount, Integer blockedCount,
            Date dateSoftban, Date datePermBan,
            Date dateCreated, Date dateAccessed, Date dateActivated,
            Date dateExpired, Date dateBlocked,
            List<TokenInfo> tokenInfos, Integer liVersion) {
        super(id, type, uuid);
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
        this.liVersion = liVersion;
    }

    @Override
    public void generateUUID() {
        super.generateUUID();
        encryptPass();
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
        Objects.requireNonNull(getUUID());

        String _pass = getPass();

        if (_pass == null || "".equals(_pass) || !_pass.endsWith(":encoded")) {
            _pass = CypherUtils.encrypt(
                    getUUID(), getUUID(), getPass());
            setPass(_pass + ":encoded");
        }
    }

    public void decryptPass() {
        Objects.requireNonNull(getUUID());

        String _pass = getPass();
        if (_pass != null && _pass.endsWith(":encoded")) {
            _pass = CypherUtils.decrypt(
                    getUUID(), getUUID(),
                    getPass().substring(
                            0, getPass().length() - ":encoded".length()));
            setPass(_pass);
        }
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
        int _hash = super.hashCode();
        _hash += (user != null ? user.hashCode() : 0);
        _hash += (firstName != null ? firstName.hashCode() : 0);
        _hash += (lastName != null ? lastName.hashCode() : 0);
        return _hash;
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
                + ", uuid=" + getUUID()
                + ", user=" + getUser() + "]";
    }
}
