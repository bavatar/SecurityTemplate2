package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String...strings) throws Exception {
        // test to see if data already exists
        if (roleRepository.findAll() == null) {
            roleRepository.save(new Role("USER"));
            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("SUPERVISOR"));

            Role adminRole = roleRepository.findByRole("ADMIN");
            Role userRole = roleRepository.findByRole("USER");
            Role supervisorRole = roleRepository.findByRole("SUPERVISOR");
            //roleRepository.findAll();

            User user = new User("jim@jim.com", "password", "Jim", "Jimmerson", true, "jim");
            user.setRoles(Arrays.asList(userRole));
            userRepository.save(user);

            // Tests
            user = userRepository.findByUsername(user.getUsername());
            System.out.println("DataLoader: User Name: " + user.getUsername() + " Password: " + user.getPassword());
            System.out.println("DataLoader: User email: " + user.getEmail());
            user = userRepository.findByUsername(user.getUsername());

            user = new User("admin@admin.com", "password", "Admin", "User", true, "admin");
            user.setRoles(Arrays.asList(adminRole));
            userRepository.save(user);
        }
    }
}