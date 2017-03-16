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
package com.rollsoftware.br.common.db.entity;

import com.rollsoftware.br.util.CypherUtils;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
@Entity
@Table(name = "OBJECT_DATA",
        schema = "ACCOUNT_MANAGER_DB_APP",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"ODID"})
            ,@UniqueConstraint(columnNames = {"ODTYPE", "ODUUIDPK"})
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
        name = "ODTYPE",
        discriminatorType = DiscriminatorType.STRING)
@XmlRootElement(name = "object")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "object", propOrder = {"id", "type", "uuid"})
public class ObjectData
        implements Serializable, ObjectInterface {

    private static final long serialVersionUID = 1L;

    @Version
    @Basic(optional = false)
    @NotNull
    @Column(name = "ODVERSION", nullable = false)
    @XmlTransient
    private Integer odVersion;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ODID", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "ODTYPE", nullable = false, length = 64)
    @XmlElement(name = "odType")
    private String type;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "ODUUIDPK", nullable = false, length = 128)
    @XmlAttribute
    @XmlID
    private String uuid;

    public ObjectData() {
        this(0);
    }

    public ObjectData(Integer id) {
        this(id, "", "");
    }

    public ObjectData(Integer id, String type, String uuid) {
        this(id, type, uuid, 0);
    }

    private ObjectData(Integer id, String type, String uuid,
            Integer odVersion) {
        this.id = id;
        this.type = type;
        this.uuid = uuid;
        this.odVersion = odVersion;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public void generateUUID() {
        String _uuid = getUUID();
        if (_uuid == null || "".equals(_uuid) || _uuid.length() != 32) {
            _uuid = CypherUtils.generateUUID();
            setUUID(_uuid);
        }
    }

    @Override
    public int hashCode() {
        int _hash = 0;
        _hash += (getId() != null ? getId().hashCode() : 0);
        _hash += (getUUID() != null ? getUUID().hashCode() : 0);
        _hash += (getType() != null ? getType().hashCode() : 0);
        return _hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work
        // in the case the id fields are not set
        if (!(object instanceof ObjectData)) {
            return false;
        }
        ObjectData other = (ObjectData) object;
        if ((this.getId() == null && other.getId() != null)
                || (this.getId() != null
                && !this.getId().equals(other.getId()))) {
            return false;
        }
        if ((this.getUUID() == null && other.getUUID() != null)
                || (this.getUUID() != null
                && !this.getUUID().equals(other.getUUID()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ObjectData[id=" + getId()
                + ", uuid=" + getUUID()
                + ", type=" + getType() + "]";
    }
}
