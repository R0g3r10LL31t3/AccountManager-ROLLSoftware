/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rollsoftware.br.accountmanager.db.app;

import java.sql.SQLException;

/**
 *
 * @author Rogério
 */
public class NotFoundEntityException extends SQLException {

    public NotFoundEntityException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public NotFoundEntityException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public NotFoundEntityException(String reason) {
        super(reason);
    }

    public NotFoundEntityException() {
    }

    public NotFoundEntityException(Throwable cause) {
        super(cause);
    }

    public NotFoundEntityException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public NotFoundEntityException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
    }

    public NotFoundEntityException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
    }

}
