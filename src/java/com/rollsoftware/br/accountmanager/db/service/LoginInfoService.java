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

import com.rollsoftware.br.accountmanager.db.DBResourceWebListener;
import com.rollsoftware.br.accountmanager.db.impl.entity.LoginInfo;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Rogério
 * @date October, 2016
 */
//@javax.ejb.Stateless
@RequestScoped
@Path("/db/login")
public class LoginInfoService
        extends AbstractServiceFacadeAsync<LoginInfo, String> {

    //@PersistenceContext(unitName = "AccountManagerPU")
    @Inject
    private EntityManager em;

    public LoginInfoService() {
        super(LoginInfo.class);
    }

    public LoginInfoService(EntityManager em) {
        this();
        this.em = em;
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public String create(LoginInfo entity)
            throws SQLException, Exception {
        entity.generateUUID();
        return super.create(entity);
    }

    @PUT
    @Override
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    public String edit(@PathParam("id") String id, LoginInfo entity)
            throws SQLException, Exception {
        return super.edit(id, entity);
    }

    @DELETE
    @Override
    @Path("{id}")
    @Produces({MediaType.TEXT_PLAIN})
    public String remove(@PathParam("id") String id)
            throws SQLException, Exception {
        return super.remove(id);
    }

    @GET
    @Override
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public LoginInfo find(@PathParam("id") String id)
            throws SQLException, Exception {
        LoginInfo loginInfo = super.find(id);
        if (loginInfo != null) {
            loginInfo.setPass(null);
        }
        return loginInfo;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<LoginInfo> findAll()
            throws SQLException, Exception {
        List<LoginInfo> loginInfoList = super.findAll();

        for (LoginInfo loginInfo : loginInfoList) {
            if (loginInfo != null) {
                loginInfo.setPass(null);
            }
        }

        return loginInfoList;
    }

    @GET
    @Override
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<LoginInfo> findRange(
            @PathParam("from") Integer from, @PathParam("to") Integer to)
            throws SQLException, Exception {
        List<LoginInfo> loginInfoList = super.findRange(from, to);

        for (LoginInfo loginInfo : loginInfoList) {
            if (loginInfo != null) {
                loginInfo.setPass(null);
            }
        }

        return loginInfoList;
    }

    @GET
    @Override
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countToString()
            throws SQLException, Exception {
        return super.countToString();
    }

    @Override
    protected EntityManager getEntityManager() {
        if (em == null) {
            //warning, this using with non EJB implemented server!
            em = DBResourceWebListener.getEntityManager();
            System.out.println(
                    "Warning: this using with non EJB or CDI implemented server!");
        }

        return em;
    }
}
