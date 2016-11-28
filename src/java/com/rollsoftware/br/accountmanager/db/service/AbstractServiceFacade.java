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
package com.rollsoftware.br.accountmanager.db.service;

import com.rollsoftware.br.accountmanager.db.entity.ObjectData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PessimisticLockScope;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author Rogério
 * @date October, 2016
 *
 * @param <T>
 * @param <ID>
 */
public abstract class AbstractServiceFacade<T extends ObjectData, ID> {

    private final Class<T> entityClass;

    public AbstractServiceFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public String create(T entity) throws SQLException, Exception {
        Callable callable = () -> {
            getEntityManager().persist(entity);
            return entity;
        };

        return "Created(entity): " + transaction(callable);
    }

    public String edit(ID id, T entity) throws SQLException, Exception {
        if (!id.equals(entity.getUUID())) {
            throw new IllegalArgumentException(
                    "ID " + id + " is not equals "
                    + "to Entity.ID " + entity.getUUID());
        }
        edit(entity);
        return "Edited(id,entity): " + entity;
    }

    public String edit(T entity) throws SQLException, Exception {
        Callable callable = () -> {
            getEntityManager().merge(entity);
            return entity;
        };

        return "Edited(entity): " + transaction(callable);
    }

    public String remove(ID id) throws SQLException, Exception {
        T entity = find(id);
        remove(entity);
        return "Removed(id): " + entity;
    }

    public String remove(T entity) throws SQLException, Exception {

        Callable callable = () -> {
            T _entity = getEntityManager().merge(entity);
            tryLockEntity(_entity);
            getEntityManager().remove(_entity);
            return _entity;
        };

        transaction(callable);
        return "Removed(entity): " + entity;
    }

    public T find(ID id) throws SQLException, Exception {
        Callable<T> callable = () -> {
            T t = getEntityManager().find(entityClass, id);
            if (t == null) {
                throw new NotFoundEntityException("Not find " + id + ".");
            }
            getEntityManager().refresh(t);
            return t;
        };

        return transaction(callable);
    }

    public List<T> findAll() throws SQLException, Exception {
        javax.persistence.criteria.CriteriaQuery cq
                = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(Integer from, Integer to)
            throws SQLException, Exception {
        javax.persistence.criteria.CriteriaQuery cq
                = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(to - from + 1);
        q.setFirstResult(from);
        return q.getResultList();
    }

    public Integer count() throws SQLException, Exception {
        javax.persistence.criteria.CriteriaQuery cq
                = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    private void tryLockEntity(T entity) throws SQLException, Exception {
        Map<String, Object> props = new HashMap();

        //props.put(QueryHints.PESSIMISTIC_LOCK_TIMEOUT, 10);
        props.put(QueryHints.PESSIMISTIC_LOCK_SCOPE,
                PessimisticLockScope.EXTENDED);

        if (!getEntityManager().getLockMode(entity)
                .equals(LockModeType.OPTIMISTIC_FORCE_INCREMENT)) {
            getEntityManager().lock(entity,
                    LockModeType.OPTIMISTIC_FORCE_INCREMENT, props);
        }
    }

    private <R extends Object> R transaction(Callable<R> statement)
            throws SQLException, Exception {
        R r = null;
        try {
            getEntityManager().getTransaction().begin();
            r = statement.call();
            getEntityManager().getTransaction().commit();
        } catch (Throwable ex) {
            if (getEntityManager().getTransaction().isActive()) {
                getEntityManager().getTransaction().rollback();
            }
            throw ex;
        }

        return r;
    }
}
