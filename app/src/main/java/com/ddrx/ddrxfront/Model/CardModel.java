package com.ddrx.ddrxfront.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by vincentshaw on 2018/3/16.
 */

@Entity
public class CardModel {
    @PrimaryKey
    private long CT_id;

    private String CT_name;
    private long U_id;
    private String UCT_time;

    private String U_name;
    private String CT_creator_name;
    private long CT_creator_id;
    private String CT_brief;
    private int CT_type;
    private int CT_privilege;
    private String CT_context;

    public long getU_id() {
        return U_id;
    }

    public void setU_id(long u_id) {
        U_id = u_id;
    }

    public CardModel(long CT_id, String CT_name, String U_name, long U_id, String UCT_time, String CT_brief, int CT_type, int CT_privilege, String CT_context, String CT_creator_name, long CT_creator_id) {
        this.CT_id = CT_id;
        this.CT_name = CT_name;
        this.U_name = U_name;
        this.U_id = U_id;
        this.UCT_time = UCT_time;
        this.CT_brief = CT_brief;
        this.CT_type = CT_type;
        this.CT_privilege = CT_privilege;
        this.CT_context = CT_context;
        this.setCT_creator_name(CT_creator_name);
        this.setCT_creator_id(CT_creator_id);
    }

    public long getCT_id() {

        return CT_id;
    }

    public void setCT_id(long CT_id) {
        this.CT_id = CT_id;
    }

    public String getCT_name() {
        return CT_name;
    }

    public void setCT_name(String CT_name) {
        this.CT_name = CT_name;
    }

    public String getU_name() {
        return U_name;
    }

    public void setU_name(String u_name) {
        U_name = u_name;
    }

    public String getCT_brief() {
        return CT_brief;
    }

    public void setCT_brief(String CT_brief) {
        this.CT_brief = CT_brief;
    }

    public int getCT_type() {
        return CT_type;
    }

    public void setCT_type(int CT_type) {
        this.CT_type = CT_type;
    }

    public int getCT_privilege() {
        return CT_privilege;
    }

    public void setCT_privilege(int CT_privilege) {
        this.CT_privilege = CT_privilege;
    }

    public String getCT_context() {
        return CT_context;
    }

    public void setCT_context(String CT_context) {
        this.CT_context = CT_context;
    }

    public String getUCT_time() {
        return UCT_time;
    }

    public void setUCT_time(String UCT_time) {
        this.UCT_time = UCT_time;
    }

    public String getCT_creator_name() {
        return CT_creator_name;
    }

    public void setCT_creator_name(String CT_creator_name) {
        this.CT_creator_name = CT_creator_name;
    }

    public long getCT_creator_id() {
        return CT_creator_id;
    }

    public void setCT_creator_id(long CT_creator_id) {
        this.CT_creator_id = CT_creator_id;
    }
}
