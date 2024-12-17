package vp.togedo.data.dto.flexiblePersonalSchedule

import com.fasterxml.jackson.annotation.JsonProperty

data class FlexiblePersonalScheduleListDto(
    @JsonProperty("flexibleSchedules")
    val schedules: List<FlexiblePersonalScheduleDto>
)