package clinicalapplication;

import com.example.clinicalapplication.models.*;
import com.example.clinicalapplication.services.BookingSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingSystemTest {

    private BookingSystem bookingSystem;

    @BeforeEach
    public void setUp() {
        bookingSystem = BookingSystem.getInstance();

        // Clear previous data
        bookingSystem.getPatients().clear();
        bookingSystem.getPhysiotherapists().clear();
        bookingSystem.getAllAppointments().clear();
    }

    @Test
    public void testAddAndGetPatient() {
        Patient patient = new Patient("P1", "Alice", "Street 1", "123456");
        bookingSystem.addPatient(patient);

        assertEquals(1, bookingSystem.getPatients().size());
        assertEquals("Alice", bookingSystem.getPatients().iterator().next().getName());
    }

    @Test
    public void testRemovePatient() {
        Patient patient = new Patient("P2", "Bob", "Street 2", "789012");
        bookingSystem.addPatient(patient);
        bookingSystem.removePatient("P2");

        assertEquals(0, bookingSystem.getPatients().size());
    }

    @Test
    public void testAddAndFindPhysiotherapistByExpertise() {
        Physiotherapist physio = new Physiotherapist("PH1", "Dr. Smith", "Clinic", "111222");
        physio.addExpertise("Physiotherapy");
        bookingSystem.addPhysiotherapist(physio);

        List<Physiotherapist> found = bookingSystem.findPhysiotherapistsByExpertise("Physiotherapy");
        assertEquals(1, found.size());
        assertEquals("Dr. Smith", found.get(0).getName());
    }

    @Test
    public void testBookAppointmentSuccess() {
        Patient patient = new Patient("P3", "Charlie", "Street 3", "333444");
        bookingSystem.addPatient(patient);

        Physiotherapist physio = new Physiotherapist("PH2", "Dr. Jane", "Clinic 2", "555666");
        physio.addExpertise("Osteopathy");
        Treatment treatment = new Treatment("Mobilisation", "Osteopathy", "Monday 10am");
        physio.addTreatment(treatment);
        bookingSystem.addPhysiotherapist(physio);

        Appointment appt = bookingSystem.bookAppointment("PH2", "P3", "Mobilisation");

        assertNotNull(appt);
        assertEquals("Charlie", appt.getPatient().getName());
        assertEquals("Dr. Jane", appt.getPhysio().getName());
    }

    @Test
    public void testBookAppointmentFailsForMissingData() {
        Appointment appt = bookingSystem.bookAppointment("NON_EXISTENT", "NO_ONE", "Nothing");
        assertNull(appt);
    }

    @Test
    public void testCancelAndAttendAppointment() {
        Patient patient = new Patient("P4", "Dana", "Lane 4", "555555");
        bookingSystem.addPatient(patient);

        Physiotherapist physio = new Physiotherapist("PH3", "Dr. Lee", "Health Hub", "777888");
        physio.addExpertise("Rehabilitation");
        Treatment treatment = new Treatment("Stretching", "Rehabilitation", "Wednesday 2pm");
        physio.addTreatment(treatment);
        bookingSystem.addPhysiotherapist(physio);

        Appointment appt = bookingSystem.bookAppointment("PH3", "P4", "Stretching");
        assertNotNull(appt);

        bookingSystem.cancelAppointment(appt);
        assertEquals(Appointment.Status.CANCELLED, appt.getStatus());

        bookingSystem.markAsAttended(appt);
        assertEquals(Appointment.Status.ATTENDED, appt.getStatus());
    }

    @Test
    public void testGetAllAppointments() {
        Patient patient = new Patient("P5", "Eve", "Road 5", "123123");
        bookingSystem.addPatient(patient);

        Physiotherapist physio = new Physiotherapist("PH4", "Dr. Max", "Therapy House", "999000");
        physio.addExpertise("Physiotherapy");
        physio.addTreatment(new Treatment("Taping", "Physiotherapy", "Friday 5pm"));
        bookingSystem.addPhysiotherapist(physio);

        bookingSystem.bookAppointment("PH4", "P5", "Taping");

        assertEquals(1, bookingSystem.getAllAppointments().size());
    }
}

