package ru.ulxanxv.sharing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public static RequestPostProcessor userHttpBasic() {
        return SecurityMockMvcRequestPostProcessors
                .httpBasic("Ulxanxv", "1234");
    }

    private void start() throws Exception {
        mockMvc.perform(get("/start"));
        mockMvc.perform(get("/user/")
                .with(userHttpBasic()));
    }

    @Test
    public void allDisks() throws Exception {
        this.mockMvc.perform(get("/user/getMyDisks")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(content().string(containsString("{\"id\":1,\"name\":\"OneDisk\"},{\"id\":2,\"name\":\"SecondDisk\"},{\"id\":3,\"name\":\"ThirdDisk\"}")));
    }

    @Test
    public void freeDisks() throws Exception {
        this.mockMvc.perform(get("/user/getFreeDisks")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(content().string(containsString("[{\"id\":5,\"name\":\"FifthDisk\"},{\"id\":4,\"name\":\"FourthDisk\"}]")));
    }

    @Test
    public void takenDisks() throws Exception {
        this.mockMvc.perform(get("/user/getTakenDisks")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void takenFromUser() throws Exception {
        this.mockMvc.perform(get("/user/getTakenFromUser")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void okRequests() throws Exception {
        // Trying to take an available disk
        this.mockMvc.perform(get("/user/getDisk/4")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(status().isOk());

        // Data has changed in the database
        this.mockMvc.perform(get("/user/getTakenDisks")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(content().string(containsString("{\"id\":4,\"name\":\"FourthDisk\"}")));

        // Returning a disc
        this.mockMvc.perform(get("/user/returnDisk/4")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(status().isOk());

        // Data has changed in the database (now not a debtor)
        this.mockMvc.perform(get("/user/getTakenDisks")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(content().string(containsString("")));
    }

    @Test
    public void tryTakeDiskFromYourself() throws Exception {
        /*
            Database bootstrap
         */
        start();

        this.mockMvc.perform(get("/user/getDisk/2")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(content().string(containsString("You cannot borrow your disc from yourself!")));
    }

    @Test
    public void tryTakeUnavailableDisk() throws Exception {
        this.mockMvc.perform(get("/user/getDisk/6")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(content().string(containsString("This disk is busy!")));
    }

    @Test
    public void tryReturnDiskThatNobodyTook() throws Exception {
        this.mockMvc.perform(get("/user/returnDisk/4")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(content().string(containsString("Nobody borrowed this disc!")));
    }

    @Test
    public void tryReturnDiskDidNotTake() throws Exception {
        this.mockMvc.perform(get("/user/returnDisk/6")
                .with(userHttpBasic()))
                .andDo(print())
                .andExpect(content().string(containsString("You cannot return this disc!")));
    }

}
