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

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Rogério
 * @date March, 2017
 */
public abstract class ObjectEmbedded implements ObjectInterface {

    public abstract ObjectInterface getParent();

    @Override
    public void generateUUID() {
        if (getParent() != null) {
            getParent().generateUUID();
        }
    }

    @Override
    public Integer getId() {
        if (getParent() != null) {
            return getParent().getId();
        }

        return null;
    }

    @Override
    @XmlAttribute(name = "uuid")
    public String getUUID() {
        if (getParent() != null) {
            return getParent().getUUID();
        }

        return null;
    }

    @Override
    public int hashCode() {
        int _hash = 0;
        _hash += (getId() != null ? getId().hashCode() : 0);
        _hash += (getUUID() != null ? getUUID().hashCode() : 0);
        return _hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work
        // in the case the id fields are not set
        if (!(object instanceof ObjectEmbedded)) {
            return false;
        }
        ObjectEmbedded other = (ObjectEmbedded) object;
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
}
