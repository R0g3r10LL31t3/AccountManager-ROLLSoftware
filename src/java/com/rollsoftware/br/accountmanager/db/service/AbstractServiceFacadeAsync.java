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

import com.rollsoftware.br.accountmanager.db.app.ServiceFacadeAsync;
import com.rollsoftware.br.accountmanager.db.app.DatabaseServiceThreadPool;
import com.rollsoftware.br.accountmanager.db.entity.ObjectData;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Rogério
 * @date November, 2016
 *
 * @param <T>
 * @param <ID>
 */
public abstract class AbstractServiceFacadeAsync<T extends ObjectData, ID>
        extends AbstractServiceFacade<T, ID>
        implements ServiceFacadeAsync<T, ID> {

    public AbstractServiceFacadeAsync(Class<T> entityClass) {
        super(entityClass);
    }

    @POST
    @Override
    @Path("async")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public void create(
            @Suspended final AsyncResponse asyncResponse,
            T entity) {
        DatabaseServiceThreadPool.invokeLater(() -> {
            try {
                asyncResponse.resume(this.create(entity));
            } catch (Exception ex) {
                asyncResponse.resume(ex);
            }
        });
    }

    @PUT
    @Override
    @Path("async/{id}")
    @Consumes(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.TEXT_PLAIN})
    public void edit(
            @Suspended final AsyncResponse asyncResponse,
            @PathParam(value = "id") ID id, T entity) {
        DatabaseServiceThreadPool.invokeLater(() -> {
            try {
                asyncResponse.resume(this.edit(id, entity));
            } catch (Exception ex) {
                asyncResponse.resume(ex);
            }
        });
    }

    @DELETE
    @Override
    @Path("async/{id}")
    @Produces({MediaType.TEXT_PLAIN})
    public void remove(
            @Suspended final AsyncResponse asyncResponse,
            @PathParam(value = "id") ID id) {
        DatabaseServiceThreadPool.invokeLater(() -> {
            try {
                asyncResponse.resume(this.remove(id));
            } catch (Exception ex) {
                asyncResponse.resume(ex);
            }
        });
    }

    @GET
    @Override
    @Path("async/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void find(
            @Suspended final AsyncResponse asyncResponse,
            @PathParam(value = "id") ID id) {
        DatabaseServiceThreadPool.invokeLater(() -> {
            try {
                asyncResponse.resume(this.find(id));
            } catch (Exception ex) {
                asyncResponse.resume(ex);
            }
        });
    }

    @GET
    @Override
    @Path("async")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void findAll(
            @Suspended final AsyncResponse asyncResponse) {
        DatabaseServiceThreadPool.invokeLater(() -> {
            try {
                asyncResponse.resume(this.findAll());
            } catch (Exception ex) {
                asyncResponse.resume(ex);
            }
        });
    }

    @GET
    @Override
    @Path("async/{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void findRange(
            @Suspended final AsyncResponse asyncResponse,
            @PathParam("from") Integer from, @PathParam("to") Integer to) {
        DatabaseServiceThreadPool.invokeLater(() -> {
            try {
                asyncResponse.resume(this.findRange(from, to));
            } catch (Exception ex) {
                asyncResponse.resume(ex);
            }
        });
    }

    @GET
    @Override
    @Path("async/count")
    @Produces(MediaType.TEXT_PLAIN)
    public void countToString(
            @Suspended final AsyncResponse asyncResponse) {
        DatabaseServiceThreadPool.invokeLater(() -> {
            try {
                asyncResponse.resume(this.countToString());
            } catch (Exception ex) {
                asyncResponse.resume(ex);
            }
        });
    }
}
