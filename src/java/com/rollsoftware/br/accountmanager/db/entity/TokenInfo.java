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
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
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
@Table(name = "TOKEN_INFO", schema = "ACCOUNT_MANAGER_DB_APP",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"TIACCESSTOKEN"})
        }
)
@DiscriminatorValue("TokenInfo")
@PrimaryKeyJoinColumn(name = "TIUUIDFK", referencedColumnName = "ODUUIDPK")
@XmlRootElement(name = "token")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "token", propOrder = {
    "accessToken", "userIP",
    "dateCreated", "dateAccessed", "dateExpires",
    "successCount", "refusedCount", "errorCount",
    "loginInfo"
})
public class TokenInfo extends ObjectData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Version
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIVERSION", nullable = false)
    @XmlTransient
    private Integer tiVersion;

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
            name = "TI_LOGININFO_UUIDFK",
            referencedColumnName = "LIUUIDFK",
            nullable = false
    )
    @XmlIDREF
    @XmlElement(name = "login")
    private LoginInfo loginInfo;

    public TokenInfo() {
        this("");
    }

    public TokenInfo(String uuid) {
        this(0, "TokenInfo", uuid,
                "", "",
                0, 0, 0,
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime(),
                null, null);
    }

    public TokenInfo(Integer id, String type, String uuid,
            String accessToken, String userIP,
            Integer successCount, Integer refusedCount, Integer errorCount,
            Date dateCreated, Date dateAccessed, Date dateExpires,
            LoginInfo loginInfo) {
        this(id, type, uuid,
                accessToken, userIP,
                successCount, refusedCount, errorCount,
                dateCreated, dateAccessed, dateExpires,
                loginInfo, 0);
    }

    public TokenInfo(Integer id, String type, String uuid,
            String accessToken, String userIP,
            Integer successCount, Integer refusedCount, Integer errorCount,
            Date dateCreated, Date dateAccessed, Date dateExpires,
            LoginInfo loginInfo, Integer tiVersion) {
        super(id, type, uuid);
        this.accessToken = accessToken;
        this.userIP = userIP;
        this.successCount = successCount;
        this.refusedCount = refusedCount;
        this.errorCount = errorCount;
        this.dateCreated = dateCreated;
        this.dateAccessed = dateAccessed;
        this.dateExpires = dateExpires;
        this.loginInfo = loginInfo;
        this.tiVersion = tiVersion;
    }

    @Override
    public void generateUUID() {
        super.generateUUID();
        generateToken();
    }

    public void generateToken() {
        Objects.requireNonNull(loginInfo.getUUID());
        Objects.requireNonNull(this.getUUID());
        Objects.requireNonNull(userIP);
        String _token = CypherUtils.generateHash(
                loginInfo.getUUID(), this.getUUID(), userIP
        );
        setAccessToken(_token);
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
        int _uuid = super.hashCode();
        _uuid += (accessToken != null ? accessToken.hashCode() : 0);
        return _uuid;
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
                + ", uuid=" + getUUID()
                + ", token=" + getAccessToken() + "]";
    }
}
