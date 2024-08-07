package med.ric.api.domain.doctor;

import med.ric.api.domain.address.AddressData;
import med.ric.api.domain.appointment.Appointment;
import med.ric.api.domain.patient.Patient;
import med.ric.api.domain.patient.PatientRegisterData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should return null when only doctor registered is not available at this time")
    void findRandomAvailableDoctorBySpecialtyTest1() {
        // Arrange
        var nextMondayAt10 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.of(10, 0));
        var doctor = registerDoctor("Doctor 1", "2bHgI@example.com", "222222", Speciality.ORTOPEDIA);
        var patient = registerPatient("Patient 1", "2bHgI@example.com", "12345678901");
        registerAppointment(doctor, patient, nextMondayAt10);

        // Act
        var freeDoctor = repository.findRandomAvailableDoctorBySpecialty(nextMondayAt10, doctor.getSpeciality());

        // Assert
        assertThat(freeDoctor).isNull();
    }

    @Test
    @DisplayName("Should return doctor when they are available at this time")
    void findRandomAvailableDoctorBySpecialtyTest2() {
        // Arrange
        var nextMondayAt10 = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.of(10, 0));
        var doctor = registerDoctor("Doctor 1", "2bHgI@example.com", "222222", Speciality.ORTOPEDIA);

        // Act
        var freeDoctor = repository.findRandomAvailableDoctorBySpecialty(nextMondayAt10, doctor.getSpeciality());

        // Assert
        assertThat(freeDoctor).isEqualTo(doctor);
    }

    private void registerAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        var appointment = new Appointment(null, doctor, patient, date);
        entityManager.persist(appointment);
    }

    private Doctor registerDoctor(String name, String email, String crm, Speciality speciality) {
        var doctor = new Doctor(doctorData(name, email, crm, speciality));
        entityManager.persist(doctor);
        return doctor;
    }

    private Patient registerPatient(String name, String email, String cpf) {
        var patient = new Patient(patientData(name, email, cpf));
        entityManager.persist(patient);
        return patient;
    }


    private DoctorRegisterData doctorData(String name, String email, String crm, Speciality speciality) {
        return new DoctorRegisterData(
            name,
            email,
            "11 011 1406",
            crm,
            speciality,
            addressData()
        );
    }

    private PatientRegisterData patientData(String name, String email, String cpf) {
        return new PatientRegisterData(
            name,
            email,
            "11 011 1406",
            cpf,
            addressData()
        );
    }

    private AddressData addressData() {
        return new AddressData("street", "neighborhood", "zipCode", "city", "ST", "details", "number");
    }
}