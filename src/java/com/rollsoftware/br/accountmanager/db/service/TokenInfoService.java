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

import com.rollsoftware.br.accountmanager.db.entity.TokenInfo;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Rogério
 * @date October, 2016
 *
 * @param <T>
 */
//@javax.ejb.Stateless
@RequestScoped
@Path("/db/token")
public class TokenInfoService
        extends AbstractServiceFacade<TokenInfo> {

    //@PersistenceContext(unitName = "AccountManagerPU")
    @Inject
    private EntityManager em;

    @Context
    private UriInfo uriInfo;

    public TokenInfoService() {
        super(TokenInfo.class);
    }

    public TokenInfoService(EntityManager em) {
        this();
        this.em = em;
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TokenInfo entity)
            throws SQLException {
        entity.generateHash();
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, TokenInfo entity)
            throws SQLException {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id)
            throws SQLException {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TokenInfo find(@PathParam("id") String id)
            throws SQLException {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TokenInfo> findAll()
            throws SQLException {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TokenInfo> findRange(
            @PathParam("from") Integer from, @PathParam("to") Integer to)
            throws SQLException {
        return super.findRange(from, to);
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countToString()
            throws SQLException {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        if (em == null) {
            //warning, this using with non EJB implemented server!
            em = EntityManagerContextListener.getEntityManager();
            System.out.println(
                    "Warning: this using with non EJB or CDI implemented server!");
        }

        return em;
    }
}
