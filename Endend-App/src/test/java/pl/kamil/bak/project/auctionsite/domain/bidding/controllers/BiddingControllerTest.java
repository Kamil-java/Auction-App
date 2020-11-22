package pl.kamil.bak.project.auctionsite.domain.bidding.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.kamil.bak.app.test.ControllerTestConfiguration;
import pl.kamil.bak.project.auctionsite.domain.bidding.service.BiddingService;
import pl.kamil.bak.project.auctionsite.domain.provider.session.UserSessionProvider;
import pl.kamil.bak.project.auctionsite.model.biddingEntity.Bidding;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BiddingController.class)
@Import(ControllerTestConfiguration.class)
class BiddingControllerTest {

    @MockBean
    private BiddingService biddingService;


    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAll() throws Exception {
        //given
        given(biddingService.getAll()).willReturn(prepareListBidding());

        //when
        final ResultActions resultActions = mockMvc.perform(get("/bidding").accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void getAllOwned() throws Exception {
        //given
        given(biddingService.getAllBiddingByUser("name")).willReturn(prepareListBidding());

        //when
        final ResultActions resultActions = mockMvc.perform(get("bidding/owned").accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void getById() throws Exception {
        //given
        given(biddingService.getBidding(1L)).willReturn(prepareListBidding().get(1));

        //when
        ResultActions resultActions = mockMvc.perform(get("/bidding/{id}", 1L).accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void addBidding() {
    }

    @Test
    void update() {
    }


    private List<Bidding> prepareListBidding(){
        return Arrays.asList(
                new Bidding(),
                new Bidding(),
                new Bidding(),
                new Bidding()
        );
    }
}