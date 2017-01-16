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

import com.rollsoftware.br.common.db.entity.ObjectInterface;
import com.rollsoftware.br.util.CypherUtils;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Rogério
 * @date December, 2016
 */
@Embeddable
@XmlRootElement(name = "credential")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "credential", propOrder = {
    "uuid", "user", "pass", "tokenInfos"
})
public class CredentialInfo implements ObjectInterface, Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    @XmlTransient
    private LoginInfo loginInfo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "CIUSER",
            unique = true, nullable = false, length = 128)
    private String user;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "CIPASS", nullable = false, length = 128)
    private String pass;

    public CredentialInfo() {
        this("", "");
    }

    public CredentialInfo(String user, String pass) {
        this(null, user, pass);
    }

    public CredentialInfo(LoginInfo loginInfo, String user, String pass) {
        this.loginInfo = loginInfo;
        this.user = user;
        this.pass = pass;
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

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        Objects.requireNonNull(loginInfo);

        this.loginInfo = loginInfo;
        if (loginInfo != null) {
            loginInfo.setCredentialInfo(this);
        }
    }

    @Override
    public void generateUUID() {
        if (loginInfo != null) {
            loginInfo.generateUUID();
        }
    }

    @Override
    public Integer getId() {
        if (loginInfo != null) {
            return loginInfo.getId();
        }

        return null;
    }

    @Override
    @XmlAttribute(name = "uuid")
    public String getUUID() {
        if (loginInfo != null) {
            return loginInfo.getUUID();
        }

        return null;
    }

    @XmlElement(name = "token")
    public List<TokenInfo> getTokenInfos() {
        if (loginInfo != null) {
            return loginInfo.getTokenInfos();
        }

        return null;
    }

    @Override
    public int hashCode() {
        int _hash = super.hashCode();
        _hash += (user != null ? user.hashCode() : 0);
        return _hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in
        // the case the id fields are not set
        if (!(object instanceof CredentialInfo)) {
            return false;
        }
        CredentialInfo other = (CredentialInfo) object;
        if ((this.getId() == null && other.getId() != null)
                || (this.getId() != null
                && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CredentialInfo[id=" + getId()
                + ", uuid=" + getUUID()
                + ", user=" + getUser() + "]";
    }
}
