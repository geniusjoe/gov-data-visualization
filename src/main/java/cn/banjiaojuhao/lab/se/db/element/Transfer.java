package cn.banjiaojuhao.lab.se.db.element;

import cn.banjiaojuhao.lab.se.account.User;
import cn.banjiaojuhao.lab.se.event.Event;

public class Transfer {
    public static Event DEvent2Event(DEvent eve1) {
        return new Event(eve1.getRecId(), eve1.getCreateTime(), eve1.getDistrictId()
                , eve1.getStreetId(), eve1.getCommunityId(), eve1.getEventTypeId(),
                eve1.getMainTypeId(), eve1.getSubTypeId(), eve1.getDisposeUnitId(),
                eve1.getEventSrcId(), eve1.getEventPropertyId(), eve1.getEventDisposeState(),
                eve1.getOperator());
    }

    public static DEvent Event2DEvent(Event eve1) {
        return new DEvent(eve1.component1(), eve1.component2(), eve1.component3(),
                eve1.component4(), eve1.component5(), eve1.component6(), eve1.component7(),
                eve1.component8(), eve1.component9(), eve1.component10(), eve1.component11(),
                eve1.component12(), eve1.component13());
    }

    public static User DUser2User(DUser usr1) {
        return new User(usr1.getUid(), usr1.getNickName(), usr1.getUsername(),
                usr1.getPassword(), usr1.getUserType(), usr1.getDepartment(),
                usr1.getPosition(), usr1.getPhone(), usr1.getEmail());
    }

    public static DUser User2DUser(User usr1){
        return new DUser(usr1.component1(),usr1.component2(),usr1.component3(),
                usr1.component4(),usr1.component5(),usr1.component6(),
                usr1.component7(),usr1.component8(),usr1.component9());
    }

}
