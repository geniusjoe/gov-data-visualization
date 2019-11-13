package cn.banjiaojuhao.lab.se.db.element;

import javax.persistence.*;

@Entity
@Table(name = "DEVENT")
public class DEvent {
    @Id
    @GeneratedValue
    @Column(name = "rec_id")
    private int recId;

    @Column(name = "create_time")
    private long createTime;
    @Column(name = "district_id")
    private int districtId;
    @Column(name = "street_id")
    private int streetId;
    @Column(name = "community_id")
    private int communityId;
    @Column(name = "event_type_id")
    private int eventTypeId;
    @Column(name = "main_type_id")
    private int mainTypeId;
    @Column(name = "sub_type_id")
    private int subTypeId;
    @Column(name = "dispose_unit_id")
    private int disposeUnitId;
    @Column(name = "event_src_id")
    private int eventSrcId;
    @Column(name = "event_property_id")
    private int eventPropertyId;
    @Column(name = "event_dispose_state")
    private int eventDisposeState;
    @Column(name = "operator")
    private int operator;

    public DEvent(int component1, long component2, int component3, int component4, int component5, int component6, int component7, int component8, int component9, int component10, int component11, int component12, int component13) {
        recId=component1;
        createTime=component2;
        districtId=component3;
        streetId=component4;
        communityId=component5;
        eventTypeId=component6;
        mainTypeId=component7;
        subTypeId=component8;
        disposeUnitId=component9;
        eventSrcId=component10;
        eventPropertyId=component11;
        eventDisposeState=component12;
        operator=component13;
    }

    public int getRecId() {
        return recId;
    }

    public void setRecId(int recId) {
        this.recId = recId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getStreetId() {
        return streetId;
    }

    public void setStreetId(int streetId) {
        this.streetId = streetId;
    }

    public int getCommunityId() {
        return communityId;
    }

    public void setCommunityId(int communityId) {
        this.communityId = communityId;
    }

    public int getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(int eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public int getMainTypeId() {
        return mainTypeId;
    }

    public void setMainTypeId(int mainTypeId) {
        this.mainTypeId = mainTypeId;
    }

    public int getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(int subTypeId) {
        this.subTypeId = subTypeId;
    }

    public int getDisposeUnitId() {
        return disposeUnitId;
    }

    public void setDisposeUnitId(int disposeUnitId) {
        this.disposeUnitId = disposeUnitId;
    }

    public int getEventSrcId() {
        return eventSrcId;
    }

    public void setEventSrcId(int eventSrcId) {
        this.eventSrcId = eventSrcId;
    }

    public int getEventPropertyId() {
        return eventPropertyId;
    }

    public void setEventPropertyId(int eventPropertyId) {
        this.eventPropertyId = eventPropertyId;
    }

    public int getEventDisposeState() {
        return eventDisposeState;
    }

    public void setEventDisposeState(int eventDisposeState) {
        this.eventDisposeState = eventDisposeState;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }
}
