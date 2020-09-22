package ru.zakharova.elena.market.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zakharova.elena.market.entities.Role;
import ru.zakharova.elena.market.entities.User;
import ru.zakharova.elena.market.entities.dtos.SystemUser;
import ru.zakharova.elena.market.exceptions.ResourceNotFoundException;
import ru.zakharova.elena.market.repositories.UsersRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService implements UserDetailsService {
    private UsersRepository usersRepository;
    private RolesService rolesService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setRolesService(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), true,
                true, true, user.isEnable(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findOneByPhone(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username"));
    }

    public Page<User> findAll(Integer page) {
        if (page < 1L) {
            page = 1;
        }
        return usersRepository.findAll(PageRequest.of(page - 1, 5));
    }

    public User findById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Can't found user with id = " + id));
    }

    public User saveOrUpdate(User user) {
        return usersRepository.save(user);
    }

    public Optional<User> findByPhone(String phone) {
        return usersRepository.findOneByPhone(phone);
    }

    @Transactional
    public User save(SystemUser systemUser) {
        User user = new User();
        findByPhone(systemUser.getPhone()).ifPresent((u) -> {
            throw new RuntimeException("User with phone " + systemUser.getPhone() + " is already exist");
        });
        user.setPhone(systemUser.getPhone());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        user.setFirstName(systemUser.getFirstName());
        user.setLastName(systemUser.getLastName());
        user.setEmail(systemUser.getEmail());
        user.setEnable(true);
        user.setRoles(Arrays.asList(rolesService.findByName("ROLE_CUSTOMER")));
        return usersRepository.save(user);
    }

}