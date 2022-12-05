package io.sudhakar.student.service.impl;

import io.sudhakar.student.dto.ServiceResponse;
import io.sudhakar.student.dto.UserDetails;
import io.sudhakar.student.entity.UserDetailsEntity;
import io.sudhakar.student.repository.UserDetailsRepository;
import io.sudhakar.student.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserDetailsRepository userRepository;

    public UserServiceImpl(UserDetailsRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ServiceResponse<List<UserDetails>> getAllUser() {

        log.info("operation = getAllUser, result = IN_PROGRESS");
        ServiceResponse<List<UserDetails>> serviceResponse = new ServiceResponse<>();
        try {
            List<UserDetailsEntity> userDetailsEntities = (List<UserDetailsEntity>) userRepository.findAll();
            List<UserDetails> userDetailsList = new ArrayList<>();

            for (UserDetailsEntity userDetailsEntity : userDetailsEntities) {
                UserDetails userDetails = new UserDetails();

                BeanUtils.copyProperties(userDetailsEntity, userDetails);
                userDetailsList.add(userDetails);
            }
            log.info("operation = getAllUser, result = SUCCESS");
            serviceResponse.setData(userDetailsList);
            serviceResponse.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = getAllUser, result = ERROR", e);
        }
        return serviceResponse;
    }

    public ServiceResponse<UserDetails> getUser(int id) {
        log.info("operation = getUser, result = IN_PROGRESS");
        ServiceResponse<UserDetails> serviceResponse = new ServiceResponse<>();
        try {
            Optional<UserDetailsEntity> userDetailsEntity = userRepository.findById(id);
            UserDetails user = new UserDetails();
            BeanUtils.copyProperties(userDetailsEntity.get(), user);

            log.info("operation = getUser, result = SUCCESS");
            serviceResponse.setData(user);
            serviceResponse.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = getUser, result = ERROR", e);
        }
        return serviceResponse;
    }

    public ServiceResponse<Void> addUser(UserDetails userDetails) {
        log.info("operation = addUser, result = IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        try {
            UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
            BeanUtils.copyProperties(userDetails, userDetailsEntity);

            userRepository.save(userDetailsEntity);

            log.info("operation = addUser, result = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = addUser, result = ERROR", e);
        }
        return serviceResponse;
    }

    public ServiceResponse<Void> updateUser(UserDetails userDetails, int id) {
        log.info("operation = addUser, result = IN_PROGRESS");
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        try {
            Optional<UserDetailsEntity> userDetailsEntity = userRepository.findById(id);

            UserDetailsEntity userEntity = userDetailsEntity.get();

            userEntity.setUsername(userDetails.getUsername());
            userEntity.setPassword(userDetails.getPassword());
            userEntity.setId(userDetails.getId());

            userRepository.save(userEntity);

            log.info("operation = addUser, result = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = addUser, result = ERROR", e);
        }
        return serviceResponse;
    }

    public ServiceResponse<Void> deleteUser(int id) {
        ServiceResponse<Void> serviceResponse = new ServiceResponse<>();
        try {
            log.info("operation = deleteUser, result = IN_PROGRESS");
            userRepository.deleteById(id);

            log.info("operation = deleteUser, result = SUCCESS");
            serviceResponse.setHttpStatus(HttpStatus.OK);
            return serviceResponse;
        } catch (Exception e) {
            serviceResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("operation = deleteUser, result = ERROR", e);
        }
        return serviceResponse;
    }

}
