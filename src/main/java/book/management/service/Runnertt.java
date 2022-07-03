package book.management.service;

import book.management.model.Role;
import book.management.model.User;
import book.management.repository.RoleRepository;
import book.management.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.logging.Logger;

@Component
public class Runnertt implements CommandLineRunner {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        roleRepository.save(new Role("admin"));


    }
}
