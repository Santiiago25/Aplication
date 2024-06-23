package com.swagger.service.implementation;

import com.swagger.persistence.entity.CompanieEntity;
import com.swagger.persistence.entity.RoleEntity;
import com.swagger.persistence.entity.UserEntity;
import com.swagger.persistence.repository.CompanieRepository;
import com.swagger.persistence.repository.RoleRepository;
import com.swagger.persistence.repository.UserRepository;
import com.swagger.presentation.dto.AuthCreateUserRequest;
import com.swagger.presentation.dto.AuthLoginRequest;
import com.swagger.presentation.dto.AuthResponse;
import com.swagger.utility.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanieRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnable(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorityList);
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtil.createToken(authentication);

        Optional<UserEntity> userCreated = userRepository.findUserEntityByUsername(username);
        if (userCreated.isPresent()) {
            UserEntity userEntity = userCreated.get();
            System.out.println("Usuario encontrado: " + userEntity.getUsername());
            System.out.println("Usuario encontrado: " + userEntity.getStatus());
            AuthResponse authResponse = new AuthResponse(username, "User logged successfully", accessToken, true, userEntity.getStatus());
            return authResponse;
        } else {
            System.out.println("No se encontró ningún usuario con el nombre de usuario: " + username);
            AuthResponse authResponse = new AuthResponse(username, "User not found", "", false, "");
            return authResponse;
        }
    }

    public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
        String username = authCreateUserRequest.username();
        String password = authCreateUserRequest.password();
        List<String> roleRequest = authCreateUserRequest.roleRequest().roleListName();
        String status = authCreateUserRequest.estado();
        Long companyId = authCreateUserRequest.companyId();

        // Nuevos campos añadidos
        String name = authCreateUserRequest.name();
        String lastName = authCreateUserRequest.lastName();
        String typeDocument = authCreateUserRequest.typeDocument();
        String numDocument = authCreateUserRequest.numDocument();
        String phone = authCreateUserRequest.phone();
        String address = authCreateUserRequest.address();

        System.out.println("Service: " + authCreateUserRequest.estado());

        Set<RoleEntity> roleEntitySet = roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());

        if (roleEntitySet.isEmpty()) {
            throw new IllegalArgumentException("The roles specified do not exist.");
        }

        CompanieEntity companyEntity = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) // Encriptando el password
                .name(name)
                .lastName(lastName)
                .typeDocument(typeDocument)
                .numDocument(numDocument)
                .phone(phone)
                .address(address)
                .roles(roleEntitySet)
                .isEnable(true)
                .accountNoLocked(true)
                .accountNoExpired(true)
                .credentialNoExpired(true)
                .status(status)
                .company(companyEntity) // Asignando la empresa al usuario
                .build();

        UserEntity userCreated = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userCreated.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
        userCreated.getRoles().stream()
                .flatMap(role -> role.getPermissionList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(), userCreated.getPassword(), authorityList);
        String accessToken = jwtUtil.createToken(authentication);

        AuthResponse authResponse = new AuthResponse(userCreated.getUsername(), "User created successfully", accessToken, true, userCreated.getStatus());

        return authResponse;
    }
}