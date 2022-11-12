package com.vladveretilnyk.workshop.application;

import com.vladveretilnyk.workshop.application.exception.ApplicationNotFoundException;
import com.vladveretilnyk.workshop.application.request.ApplicationCreateRequest;
import com.vladveretilnyk.workshop.application.request.ApplicationUpdateInfoRequest;
import com.vladveretilnyk.workshop.application.status.CompletionStatus;
import com.vladveretilnyk.workshop.user.User;
import com.vladveretilnyk.workshop.user.UserRole;
import com.vladveretilnyk.workshop.user.UserService;
import com.vladveretilnyk.workshop.user.exception.UserNotFountException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@AllArgsConstructor
public class ApplicationService {

    private ApplicationRepository applicationRepository;
    private UserService userService;

    private ModelMapper modelMapper;

    public void create(ApplicationCreateRequest applicationCreateRequest) throws UserNotFountException {
        User user = userService.findById(applicationCreateRequest.getAuthorId());
        Application application = modelMapper.map(applicationCreateRequest, Application.class);

        addUser(application, user);

        save(application);
    }

    public void save(Application application) {
        applicationRepository.save(application);
    }

    public List<Application> findAllByUser(User user) {
        if (user.getAuthorities().contains(UserRole.ADMIN)) {
            return applicationRepository.findAll();
        }

        return applicationRepository.findAllByUsersContains(user);
    }

    public Application findById(Long id) throws ApplicationNotFoundException {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(format("Application with id %d not found!", id)));
    }

    public void deleteById(Long id) throws ApplicationNotFoundException {
        Application application = findById(id);

        application.getUsers().forEach(user -> user.getApplications().remove(application));
        application.getUsers().clear();

        applicationRepository.delete(application);
    }

    public void updateById(Long id, ApplicationUpdateInfoRequest applicationUpdateInfoRequest) throws ApplicationNotFoundException {
        Application application = findById(id);
        modelMapper.map(applicationUpdateInfoRequest, application);

        save(application);
    }

    public void assignApplicationToMaster(Long applicationId, Long masterId) throws UserNotFountException, ApplicationNotFoundException {
        Application application = findById(applicationId);
        User master = userService.findById(masterId);

        addUser(application, master);

        save(application);
    }

    public void unassignApplicationFromMaster(Long applicationId, Long masterId) throws UserNotFountException, ApplicationNotFoundException {
        Application application = findById(applicationId);
        User master = userService.findById(masterId);

        removeUser(application, master);

        save(application);
    }

    public void setCompletionStatusForApplicationById(Long id, CompletionStatus status) throws ApplicationNotFoundException {
        Application application = findById(id);
        application.setCompletionStatus(status);

        save(application);
    }

    private void addUser(Application application, User user) {
        application.getUsers().add(user);
        user.getApplications().add(application);
    }

    private void removeUser(Application application, User user) {
        application.getUsers().remove(user);
        user.getApplications().remove(application);
    }

}
