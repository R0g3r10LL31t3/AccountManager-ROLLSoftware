/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rollsoftware.br.accountmanager.db.entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rogério
 */
//@MappedSuperclass
@Entity
@Table(name = "OBJECT_DATA",
        schema = "ACCOUNT_MANAGER_DB_APP",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"ODTYPE", "ODHASH"})
        }
)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
        name = "ODTYPE",
        discriminatorType = DiscriminatorType.STRING)
@XmlRootElement
public class ObjectData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ODIDPK", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "ODTYPE", nullable = false, length = 64)
    private String type;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "ODHASH", nullable = false, length = 128)
    private String hash;

    public ObjectData() {
        this(0, "", "");
    }

    public ObjectData(Integer id) {
        this(id, "", "");
    }

    public ObjectData(Integer id, String type, String hash) {
        this.id = id;
        this.type = type;
        this.hash = hash;
    }

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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public int hashCode() {
        int _hash = 0;
        _hash += (id != null ? id.hashCode() : 0);
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
        if ((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ObjectData[id=" + getId() + ", type=" + getType() + "]";
    }
}
