package tacos.security;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tacos.User;
import tacos.data.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class RegistrationControllerTest {

    @Test
    void testRegisterForm() throws Exception {
        RegistrationController controller = new RegistrationController(null, null);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/register"))
                .andExpect(view().name("registration"));
    }

    @Test
    void testProcessRegistration() throws Exception {
        UserRepository userRepo = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        RegistrationController controller = new RegistrationController(userRepo, passwordEncoder);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(post("/register")
                        .param("username", "testuser")
                        .param("password", "password")
                        .param("fullname", "Test User")
                        .param("street", "123 Main St")
                        .param("city", "Testville")
                        .param("state", "TS")
                        .param("zip", "12345")
                        .param("phone", "1234567890")
                        .param("email", "test@example.com"))
                .andExpect(redirectedUrl("/login"));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("testuser", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals("Test User", savedUser.getFullname());
        assertEquals("123 Main St", savedUser.getStreet());
        assertEquals("Testville", savedUser.getCity());
        assertEquals("TS", savedUser.getState());
        assertEquals("12345", savedUser.getZip());
        assertEquals("1234567890", savedUser.getPhoneNumber());
        assertEquals("test@example.com", savedUser.getEmail());
    }
}
