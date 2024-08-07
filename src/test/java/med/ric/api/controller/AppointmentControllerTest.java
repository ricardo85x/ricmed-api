package med.ric.api.controller;

import med.ric.api.domain.appointment.*;
import med.ric.api.domain.doctor.Speciality;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ScheduleAppointmentData> scheduleAppointmentDataJson;

    @Autowired
    private JacksonTester<DetailsAppointmentData> detailsAppointmentDataJson;

    @MockBean
    private ScheduleAppointmentService scheduleAppointmentServiceMock;

    @Test
    @DisplayName("Should return 400 when data is invalid")
    @WithMockUser
    void scheduleTest1() throws Exception {
        // Act
        var response = mvc.perform(post("/appointments"))
                .andReturn().getResponse();

        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return 200 when data is valid")
    @WithMockUser
    void scheduleTest2() throws Exception {

        // Arrange
        var date = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.of(10, 0));
        var speciality = Speciality.CARDIOLOGIA;
        var doctorId = 1L;
        var patientId = 1L;

        var dtoSchedule = new ScheduleAppointmentData(
                doctorId,
                patientId,
                date,
                speciality
        );

        var dtoDetails = new DetailsAppointmentData(
                null,
                dtoSchedule.doctorId(),
                dtoSchedule.patientId(),
                dtoSchedule.date()
        );

        when(scheduleAppointmentServiceMock.schedule(dtoSchedule)).thenReturn(dtoDetails);


        // Act
        var response = mvc
                .perform(
                        post("/appointments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(scheduleAppointmentDataJson.write(
                                        new ScheduleAppointmentData(
                                                doctorId,
                                                patientId,
                                                date,
                                                speciality
                                        )
                                ).getJson()))
                .andReturn().getResponse();


        // Assert
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectedJson = detailsAppointmentDataJson.write(
                dtoDetails
        ).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }
}