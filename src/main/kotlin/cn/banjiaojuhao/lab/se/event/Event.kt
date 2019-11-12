package cn.banjiaojuhao.lab.se.event

import com.alibaba.fastjson.annotation.JSONField

data class Event(
        @JSONField(name = "rec_id") val recId: Int,
        @JSONField(name = "create_time") val createTime: Long,
        @JSONField(name = "district_id") val districtId: Int,
        @JSONField(name = "street_id") val streetId: Int,
        @JSONField(name = "community_id") val communityId: Int,
        @JSONField(name = "event_type_id") val eventTypeId: Int,
        @JSONField(name = "main_type_id") val mainTypeId: Int,
        @JSONField(name = "sub_type_id") val subTypeId: Int,
        @JSONField(name = "dispose_unit_id") val disposeUnitId: Int,
        @JSONField(name = "event_src_id") val eventSrcId: Int,
        @JSONField(name = "event_property_id") val eventPropertyId: Int,
        @JSONField(name = "event_dispose_state") val eventDisposeState: Int,
        /** uid of keyboarder */
        @JSONField(name = "operator") val operator: Int)

data class Overview(
        @JSONField(name = "overall") val overall: Overall,
        @JSONField(name = "event_property") val eventProperty: List<EventProperty>,
        @JSONField(name = "street_sub_type") val streetSubtype: List<StreetSubtype>,
        @JSONField(name = "hot_community") val hotCommunity: List<HotCommunity>,
        @JSONField(name = "archive") val archiveEvent: List<ArchiveEvent>,
        @JSONField(name = "word_cloud") val wordCloud: List<WordCloud>,
        @JSONField(name = "event_src") val eventSrc: List<EventSrc>
)

data class Overall(
        @JSONField(name = "disposed") val disposed: Int,
        @JSONField(name = "to_dispose") val toDispose: Int,
        @JSONField(name = "complaint") val complaint: Int,
        @JSONField(name = "service_index") var serviceIndex: Int = 0
)

data class EventProperty(
        @JSONField(name = "property_id") val propertyId: Int,
        @JSONField(name = "count") val count: Int
)

data class StreetSubtype(
        @JSONField(name = "street_id") val streetId: Int,
        @JSONField(name = "sub_type_id") val subTypeId: Int,
        @JSONField(name = "count") val count: Int
)

data class HotCommunity(
        @JSONField(name = "community_id") val communityId: Int,
        @JSONField(name = "count") val count: Int
)

data class ArchiveEventDetail(
        @JSONField(name = "event_type_id") val eventTypeId: Int,
        @JSONField(name = "count") val count: Int
)

data class ArchiveEvent(
        @JSONField(name = "state") val state: Int,
        @JSONField(name = "count") val count: Int,
        @JSONField(name = "detail") val detail: ArchiveEventDetail
)

data class WordCloud(
        @JSONField(name = "main_type_id") val mainTypeId: Int,
        @JSONField(name = "count") val count: Int
)

data class EventSrc(
        @JSONField(name = "event_src_id") val eventSrcId: Int,
        @JSONField(name = "count") val count: Int
)

data class ShortEvent(
        @JSONField(name = "create_time") val createTime: Long,
        @JSONField(name = "street_id") val streetId: Int,
        @JSONField(name = "community_id") val communityId: Int,
        @JSONField(name = "event_src_id") val eventSrcId: Int,
        @JSONField(name = "sub_type_id") val subTypeId: Int,
        @JSONField(name = "event_property_id") val eventPropertyId: Int,
        @JSONField(name = "event_dispose_state") val eventDisposeState: Int
)

data class TimePassed(
        @JSONField(name = "dispose_unit_id") val disposeUnitId: Int,
        @JSONField(name = "time_passed") val timePassed: Long
)

data class DepartmentKpi(
        @JSONField(name = "dispose_unit_id") val disposeUnitId: Int,
        @JSONField(name = "complaint") val complaint: Int,
        @JSONField(name = "thanks") val thanks: Int,
        @JSONField(name = "disposing") val disposing: Int,
        @JSONField(name = "overtime") val overtime: Int,
        @JSONField(name = "intime") val intime: Int,
        @JSONField(name = "service_index") val serviceIndex: Int
)