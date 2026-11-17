package com.example.healthcare_customercare.config;

import com.example.healthcare_customercare.entity.Doctor;
import com.example.healthcare_customercare.entity.Room;
import com.example.healthcare_customercare.entity.DoctorSchedule;
import com.example.healthcare_customercare.repository.DoctorRepository;
import com.example.healthcare_customercare.repository.RoomRepository;
import com.example.healthcare_customercare.repository.DoctorScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DoctorRepository doctorRepository;
    
    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;
    

    @Override
    public void run(String... args) throws Exception {
        // Check if doctors already exist
        if (doctorRepository.count() == 0) {
            // Add sample doctors
            Doctor doctor1 = new Doctor(
                "Dr. Sarah Johnson",
                "Cardiology",
                "Cardiology Department",
                "sarah.johnson@arogya.com"
            );
            
            Doctor doctor2 = new Doctor(
                "Dr. Michael Chen",
                "Pediatrics",
                "Pediatrics Department",
                "michael.chen@arogya.com"
            );
            
            Doctor doctor3 = new Doctor(
                "Dr. Emily Rodriguez",
                "General Medicine",
                "General Medicine Department",
                "emily.rodriguez@arogya.com"
            );
            
            Doctor doctor4 = new Doctor(
                "Dr. David Kim",
                "Orthopedics",
                "Orthopedics Department",
                "david.kim@arogya.com"
            );
            
            Doctor doctor5 = new Doctor(
                "Dr. Lisa Wang",
                "Dermatology",
                "Dermatology Department",
                "lisa.wang@arogya.com"
            );

            doctorRepository.save(doctor1);
            doctorRepository.save(doctor2);
            doctorRepository.save(doctor3);
            doctorRepository.save(doctor4);
            doctorRepository.save(doctor5);
            
            System.out.println("Sample doctors added to database!");
        } else {
            System.out.println("Doctors already exist in database. Count: " + doctorRepository.count());
        }
        
        // Check if rooms already exist
        if (roomRepository.count() == 0) {
            // Add sample rooms with different capacities
            Room room1 = new Room(10); // room_capacity = 10, current_capacity = 0, remaining_capacity = 10
            Room room2 = new Room(15); // room_capacity = 15, current_capacity = 0, remaining_capacity = 15
            Room room3 = new Room(20); // room_capacity = 20, current_capacity = 0, remaining_capacity = 20
            Room room4 = new Room(10); // room_capacity = 10, current_capacity = 0, remaining_capacity = 10
            Room room5 = new Room(12); // room_capacity = 12, current_capacity = 0, remaining_capacity = 12
            Room room6 = new Room(8);  // room_capacity = 8, current_capacity = 0, remaining_capacity = 8
            Room room7 = new Room(25); // room_capacity = 25, current_capacity = 0, remaining_capacity = 25
            Room room8 = new Room(6);  // room_capacity = 6, current_capacity = 0, remaining_capacity = 6
            Room room9 = new Room(18); // room_capacity = 18, current_capacity = 0, remaining_capacity = 18
            Room room10 = new Room(14); // room_capacity = 14, current_capacity = 0, remaining_capacity = 14

            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);
            roomRepository.save(room4);
            roomRepository.save(room5);
            roomRepository.save(room6);
            roomRepository.save(room7);
            roomRepository.save(room8);
            roomRepository.save(room9);
            roomRepository.save(room10);
            
            System.out.println("Sample rooms added to database!");
        } else {
            System.out.println("Rooms already exist in database. Count: " + roomRepository.count());
        }
        
        // Check if doctor schedules already exist
        if (doctorScheduleRepository.count() == 0) {
            // Get all doctors and rooms
            java.util.List<Doctor> doctors = doctorRepository.findAll();
            java.util.List<Room> rooms = roomRepository.findAll();
            
            if (!doctors.isEmpty() && !rooms.isEmpty()) {
                // Create schedules for the next 7 days
                java.time.LocalDate today = java.time.LocalDate.now();
                
                for (int day = 0; day < 7; day++) {
                    java.time.LocalDate scheduleDate = today.plusDays(day);
                    
                    // Create schedules for each doctor
                    for (int i = 0; i < doctors.size() && i < rooms.size(); i++) {
                        Doctor doctor = doctors.get(i);
                        Room room = rooms.get(i);
                        
                        // Morning schedule (8:00 AM - 12:00 PM)
                        DoctorSchedule morningSchedule = new DoctorSchedule(
                            scheduleDate,
                            java.time.LocalTime.of(8, 0),
                            java.time.LocalTime.of(12, 0),
                            "Morning",
                            doctor.getDocId(),
                            room.getRoomId()
                        );
                        
                        // Afternoon schedule (1:00 PM - 5:00 PM)
                        DoctorSchedule afternoonSchedule = new DoctorSchedule(
                            scheduleDate,
                            java.time.LocalTime.of(13, 0),
                            java.time.LocalTime.of(17, 0),
                            "Afternoon",
                            doctor.getDocId(),
                            room.getRoomId()
                        );
                        
                        // Evening schedule (6:00 PM - 10:00 PM)
                        DoctorSchedule eveningSchedule = new DoctorSchedule(
                            scheduleDate,
                            java.time.LocalTime.of(18, 0),
                            java.time.LocalTime.of(22, 0),
                            "Evening",
                            doctor.getDocId(),
                            room.getRoomId()
                        );
                        
                        doctorScheduleRepository.save(morningSchedule);
                        doctorScheduleRepository.save(afternoonSchedule);
                        doctorScheduleRepository.save(eveningSchedule);
                    }
                }
                
                System.out.println("Sample doctor schedules added to database!");
            } else {
                System.out.println("Cannot create schedules: No doctors or rooms found!");
            }
        } else {
            System.out.println("Doctor schedules already exist in database. Count: " + doctorScheduleRepository.count());
        }
        
    }
}
