package cn.banjiaojuhao.lab.se.db.event;

import cn.banjiaojuhao.lab.se.db.middleware.OPEvent;
import cn.banjiaojuhao.lab.se.dbinterface.IEvent;
import cn.banjiaojuhao.lab.se.event.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DbEvent implements IEvent {

    @NotNull
    @Override
    public List<Event> queryAppeal(int source) {
        OPEvent eve = new OPEvent();
        return eve.queryAppeal(source);
    }

    @Override
    public void addAppeal(@NotNull Event event) {
        OPEvent eve = new OPEvent();
        eve.addAppeal(event);
    }

    @Override
    public void deleteAppeal(int rec_id) {
        OPEvent eve = new OPEvent();
        eve.deleteAppeal(rec_id);
    }

    @Override
    public void updateAppeal(@NotNull Event event) {
        OPEvent eve = new OPEvent();
        eve.updateAppeal(event);
    }

    @NotNull
    @Override
    public Overall queryOverall(long startTime, long endTime) {
        OPEvent eve = new OPEvent();
        return eve.queryOverall(startTime,endTime);
    }

    @NotNull
    @Override
    public List<HotCommunity> queryHotCommunity(long startTime, long endTime) {
        OPEvent eve = new OPEvent();
        return eve.queryHotCommunity(startTime,endTime);
    }

    @NotNull
    @Override
    public List<EventProperty> queryProperty(long startTime, long endTime) {
        OPEvent eve = new OPEvent();
        return eve.queryProperty(startTime,endTime);
    }

    @NotNull
    @Override
    public List<ArchiveEvent> queryArchiveEvent(long startTime, long endTime) {
        OPEvent eve = new OPEvent();
        return eve.queryArchiveEvent(startTime,endTime);
    }

    @NotNull
    @Override
    public List<WordCloud> queryWordCloud(long startTime, long endTime) {
        OPEvent eve = new OPEvent();
        return eve.queryWordCloud(startTime, endTime);
    }

    @NotNull
    @Override
    public List<StreetSubtype> queryStreetSubtype(long startTime, long endTime) {
        OPEvent eve = new OPEvent();
        return eve.queryStreetSubtype(startTime, endTime);
    }

    @NotNull
    @Override
    public List<EventSrc> queryEventSrc(long startTime, long endTime) {
        OPEvent eve = new OPEvent();
        return eve.queryEventSrc(startTime, endTime);
    }

    @NotNull
    @Override
    public List<ShortEvent> queryShortEvent(long timeAfter) {
        OPEvent eve = new OPEvent();
        return eve.queryShortEvent(timeAfter);
    }

    @NotNull
    @Override
    public List<Event> queryUndisposedAppeal() {
        OPEvent eve = new OPEvent();
        return eve.queryUndisposedAppeal();
    }

    @NotNull
    @Override
    public List<TimePassed> queryTimePassed() {
        OPEvent eve = new OPEvent();
        return eve.queryTimePassed();
    }

    @NotNull
    @Override
    public List<DepartmentKpi> queryDepartmentKpi() {
        OPEvent eve = new OPEvent();
        return eve.queryDepartmentKpi();
    }
}
