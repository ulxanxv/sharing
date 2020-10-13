package ru.ulxanxv.sharing;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
public class MainControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void allDisks() throws Exception {
        this.mockMvc.perform(get("/user/disks/all")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(content().string(containsString("[{\"id\":1,\"name\":\"First Disk\"},{\"id\":2,\"name\":\"Second Disk\"},{\"id\":3,\"name\":\"Third Disk\"}]")));
    }

    @Test
    public void freeDisks() throws Exception {
        this.mockMvc.perform(get("/user/disks/free")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(content().string(containsString("[{\"id\":4,\"name\":\"Fourth Disk\"}]")));
    }

    @Test
    public void takenDisks() throws Exception {
        this.mockMvc.perform(get("/user/disks/taken/by_me")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(content().string(containsString("[{\"id\":6,\"name\":\"Sixth Disk\"},{\"id\":7,\"name\":\"Seventh Disk\"}]")));
    }

    @Test
    public void takenFromUser() throws Exception {
        this.mockMvc.perform(get("/user/disks/taken/from_me")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void okRequests() throws Exception {
        // Trying to take an available disk
        this.mockMvc.perform(put("/user/disk/take/4")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(status().isOk());

        // Data has changed in the database
        this.mockMvc.perform(get("/user/disks/taken/by_me")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(content().string(containsString("[{\"id\":4,\"name\":\"Fourth Disk\"},{\"id\":6,\"name\":\"Sixth Disk\"},{\"id\":7,\"name\":\"Seventh Disk\"}]")));

        // Returning a disc
        this.mockMvc.perform(put("/user/disk/return/4")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(status().isOk());

        // Data has changed in the database (now not a debtor)
        this.mockMvc.perform(get("/user/disks/taken/by_me")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(content().string(containsString("[{\"id\":6,\"name\":\"Sixth Disk\"},{\"id\":7,\"name\":\"Seventh Disk\"}]")));
    }

    @Test
    public void tryTakeDiskFromYourself() throws Exception {
        this.mockMvc.perform(put("/user/disk/take/1")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(content().string(containsString("You cannot borrow your disc from yourself!")));
    }

    @Test
    public void tryTakeUnavailableDisk() throws Exception {
        this.mockMvc.perform(put("/user/disk/take/6")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(content().string(containsString("This disk is busy!")));
    }

    @Test
    public void tryReturnDiskThatNobodyTook() throws Exception {
        this.mockMvc.perform(put("/user/disk/return/4")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(content().string(containsString("Nobody borrowed this disc!")));
    }

    @Test
    public void tryReturnDiskDidNotTake() throws Exception {
        this.mockMvc.perform(put("/user/disk/return/5")
                .with(httpBasic("Ulxanxv", "1234")))
                .andDo(print())
                .andExpect(content().string(containsString("You cannot return this disc!")));
    }

}
