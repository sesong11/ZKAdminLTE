package com.sample.ZKSpringJPA.viewmodel.authentication;

import com.sample.ZKSpringJPA.entity.authentication.User;
import com.sample.ZKSpringJPA.entity.request.RequestForm;
import com.sample.ZKSpringJPA.services.authentication.UserService;
import com.sample.ZKSpringJPA.utils.FeaturesScanner;
import com.sample.ZKSpringJPA.utils.UserCredentialService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ChangePasswordDialogVM {

    @WireVariable
    private UserCredentialService userCredentialService;

    @WireVariable
    private UserService userService;

    @Getter
    private User currentUser;

    @NotEmpty(message = "This field cannot empty.")
    @Setter @Getter
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$" , message = "Must be at least 6 characters, character, numeric, special character.")
    @Setter @Getter
    private String newPassword;

    @Setter @Getter
    private String confirmPassword;

    @Setter @Getter
    private String oldPasswordNotMatch;
    @Setter @Getter
    private String newPasswordNotMatch;

    @Init
    public void init() {
        currentUser = userCredentialService.getCurrentEmployee().getUser();
    }
    @Command
    public void close(@BindingParam("window") Window dialog) throws ServletException {

       if(isForce()) {
           logout();
       }
       dialog.detach();

    }

    @NotifyChange({"oldPasswordNotMatch", "newPasswordNotMatch"})
    @Command
    public void confirm(@BindingParam("window") Window dialog) {

       if(!isValidOldPassword()) {
           return;
       }
       if(!newPassword.equals(confirmPassword)) {
            newPasswordNotMatch = "New Password and Confirm Password must be the same.";
            return;
       }
       PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
       String encodedPassword = passwordEncoder.encode(newPassword);
       currentUser.setPassword(encodedPassword);
       userService.updateUser(currentUser);
       //if(isForce()) {
           Executions.sendRedirect("/");
       //}
       dialog.detach();
    }


    @NotifyChange({"oldPasswordNotMatch"})
    @Command
    public boolean isValidOldPassword() {
        if(!BCrypt.checkpw(oldPassword,currentUser.getPassword())) {
            oldPasswordNotMatch = "Old Password is not correct.";
            return false;
        }
        oldPasswordNotMatch = "";
        return true;
    }

    public void logout() throws ServletException {
        FeaturesScanner.getFeatures().clear();
        HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
        Executions.sendRedirect(response.encodeRedirectURL("/logout"));
        Executions.getCurrent().setVoided(true);
    }

    public boolean isForce() {
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMyy");
        String password = formatter.format(userCredentialService.getCurrentEmployee().getDob());
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return BCrypt.checkpw(password,currentUser.getPassword());
    }


}
