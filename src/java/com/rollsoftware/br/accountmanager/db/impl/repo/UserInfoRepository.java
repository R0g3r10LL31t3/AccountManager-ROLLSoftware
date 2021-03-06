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
package com.rollsoftware.br.accountmanager.db.impl.repo;

import com.rollsoftware.br.accountmanager.db.impl.entity.LoginInfo;
import com.rollsoftware.br.accountmanager.db.impl.entity.UserInfo;
import com.rollsoftware.br.common.db.repo.EmbeddedAbstractRepository;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Rogério
 * @date December, 2016
 */
@RequestScoped
public class UserInfoRepository
        extends EmbeddedAbstractRepository<
        LoginInfo, UserInfo, String> {

    public UserInfoRepository() {
        super(new LoginInfoRepository());
    }

    @Override
    protected LoginInfo extractParent(UserInfo entity) {
        return entity.getLoginInfo();
    }

    @Override
    protected UserInfo extractChild(LoginInfo entity) {
        return entity.getUserInfo();
    }
}
