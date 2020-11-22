package pl.kamil.bak.project.auctionsite.domain.bidding.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.kamil.bak.app.test.ControllerTestConfiguration;
import pl.kamil.bak.project.auctionsite.domain.bidding.dto.BiddingDto;
import pl.kamil.bak.project.auctionsite.domain.bidding.service.BiddingService;
import pl.kamil.bak.project.auctionsite.model.biddingEntity.Bidding;
import pl.kamil.bak.project.auctionsite.model.productEntity.Product;
import pl.kamil.bak.project.auctionsite.model.userEntity.User;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BiddingController.class)
@Import(ControllerTestConfiguration.class)
class BiddingControllerTest {

    @MockBean
    private BiddingService biddingService;


    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser
    void getAll() throws Exception {
        //given
        given(biddingService.getAll()).willReturn(prepareListBidding());

        //when
        final ResultActions resultActions = mockMvc.perform(get("/bidding").accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(3))
                .andDo(print());
    }

    @Test
    void getAllOwned() throws Exception {
        //given
        given(biddingService.getAllBiddingByUser("name")).willReturn(prepareListBidding());

        //when
        final ResultActions resultActions = mockMvc.perform(get("/bidding/owned"));

        //then
        resultActions
                .andExpect(status().isFound())
                .andDo(print());
    }

    @Test
    @WithMockUser
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
    @WithMockUser
    void addBidding() throws Exception {
        //given
        given(biddingService.crateBidding(prepareBiddingDto(), prepareUser())).willReturn(prepareBidding());

        //when
        ResultActions resultActions = mockMvc.perform(post("/bidding").with(csrf()));

        //then
        resultActions
                .andExpect(status().isForbidden())
                .andDo(print());

    }

    @Test
    void update() throws Exception {
        //given
        given(biddingService.updatePrice(prepareUser(), 2.00, 1L)).willReturn(prepareBidding());

        //when
        ResultActions resultActions = mockMvc.perform(put("/bidding/{id}", 1L));

        //then
        resultActions
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    private User prepareUser() {
        User user = new User();
        user.setUserName("name");
        user.setEmail("name");
        user.setId(1L);
        return user;
    }

    private Product prepareProduct() {
        Product product = new Product();
        product.setUser(prepareUser());
        product.setId(1L);
        return product;
    }

    private Bidding prepareBidding() {
        Bidding bidding = new Bidding();
        bidding.setUser(prepareUser());
        bidding.setId(1L);
        bidding.setProduct(prepareProduct());
        return bidding;
    }

    private BiddingDto prepareBiddingDto() {
        BiddingDto biddingDto = new BiddingDto();
        biddingDto.setUser(prepareUser());
        return biddingDto;
    }


    private List<Bidding> prepareListBidding() {
        return Arrays.asList(
                prepareBidding(),
                prepareBidding(),
                prepareBidding()
        );
    }
}