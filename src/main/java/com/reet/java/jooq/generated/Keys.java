/*
 * This file is generated by jOOQ.
 */
package com.reet.java.jooq.generated;


import com.reet.java.jooq.generated.tables.Bank;
import com.reet.java.jooq.generated.tables.Bankaccount;
import com.reet.java.jooq.generated.tables.records.BankRecord;
import com.reet.java.jooq.generated.tables.records.BankaccountRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * PUBLIC.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<BankRecord> CONSTRAINT_1 = Internal.createUniqueKey(Bank.BANK, DSL.name("CONSTRAINT_1"), new TableField[] { Bank.BANK.BANK_ID }, true);
    public static final UniqueKey<BankaccountRecord> CONSTRAINT_E = Internal.createUniqueKey(Bankaccount.BANKACCOUNT, DSL.name("CONSTRAINT_E"), new TableField[] { Bankaccount.BANKACCOUNT.ACCOUNT_ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<BankaccountRecord, BankRecord> CONSTRAINT_EA = Internal.createForeignKey(Bankaccount.BANKACCOUNT, DSL.name("CONSTRAINT_EA"), new TableField[] { Bankaccount.BANKACCOUNT.BANK_ID }, Keys.CONSTRAINT_1, new TableField[] { Bank.BANK.BANK_ID }, true);
}
