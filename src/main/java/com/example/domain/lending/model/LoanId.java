package com.example.domain.lending.model;

import com.example.domain.shared.valueobject.EntityId;

/**
 * Value Object para ID de Loan
 */
public class LoanId extends EntityId {
    
    public LoanId(String value) {
        super(value);
    }

    public LoanId() {
        super();
    }

    public static LoanId generate() {
        return new LoanId();
    }

    public static LoanId of(String value) {
        return new LoanId(value);
    }
}