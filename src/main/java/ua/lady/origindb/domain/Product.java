package ua.lady.origindb.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by stani on 21-Jun-17.
 */
@Entity @Table(name = "ASSORTIMENT")
@Data
public class Product {
    @Id
    @Column(name = "NUMBER")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "GRUPA")
    private String group;

    @Column(name = "MADE")
    private String brand;

    @Column(name = "TYP")
    private String gender;

    @Column(name = "BULK")
    private String volume;

    @Column(name = "VID")
    private String type;
    /*NUMBER	INTEGER NOT NULL,
    CODE_ID	INTEGER,
    GRUPA	VARCHAR(36) CHARACTER SET WIN1251 COLLATE PXW_CYRL,
    NAME	VARCHAR(50) CHARACTER SET WIN1251 COLLATE PXW_CYRL,
    MADE	VARCHAR(36) CHARACTER SET WIN1251 COLLATE PXW_CYRL,
    VERSIA	INTEGER,
    TYP	VARCHAR(10) CHARACTER SET WIN1251 COLLATE PXW_CYRL,
    BULK	VARCHAR(10) CHARACTER SET WIN1251 COLLATE PXW_CYRL,
    VID	VARCHAR(36) CHARACTER SET WIN1251 COLLATE PXW_CYRL,
    CENA_P	NUMERIC(15, 2),
    CENA_R	NUMERIC(15, 2),*/
}
