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
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Rogério
 * @date December, 2016
 */
@Embeddable
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
    "uuid", "firstName", "lastName"
})
public class UserInfo implements ObjectInterface, Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    @XmlTransient
    private LoginInfo loginInfo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "UIFIRSTNAME", nullable = false, length = 64)
    private String firstName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "UILASTNAME", nullable = false, length = 256)
    private String lastName;

    public UserInfo() {
        this("", "");
    }

    public UserInfo(String firstName, String lastName) {
        this(null, firstName, lastName);
    }

    public UserInfo(LoginInfo loginInfo, String firstName, String lastName) {
        this.loginInfo = loginInfo;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        Objects.requireNonNull(loginInfo);

        this.loginInfo = loginInfo;
        if (loginInfo != null) {
            loginInfo.setUserInfo(this);
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

    @Override
    public int hashCode() {
        int _hash = super.hashCode();
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
        UserInfo other = (UserInfo) object;
        if ((this.getId() == null && other.getId() != null)
                || (this.getId() != null
                && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserInfo[id=" + getId()
                + ", uuid=" + getUUID()
                + ", firstName=" + getFirstName() + "]";
    }
}
