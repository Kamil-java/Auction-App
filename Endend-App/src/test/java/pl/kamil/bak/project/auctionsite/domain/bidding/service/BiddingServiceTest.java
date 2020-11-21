package pl.kamil.bak.project.auctionsite.domain.bidding.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.server.ResponseStatusException;
import pl.kamil.bak.project.auctionsite.domain.bidding.dao.BiddingRepository;
import pl.kamil.bak.project.auctionsite.domain.bidding.dto.BiddingDto;
import pl.kamil.bak.project.auctionsite.model.biddingEntity.Bidding;
import pl.kamil.bak.project.auctionsite.model.userEntity.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class BiddingServiceTest {

    @Mock
    private BiddingRepository biddingRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BiddingService biddingService;

    @BeforeEach
    public void init(){
        given(biddingRepository.findAll()).willReturn(prepareBidding());
        given(biddingRepository.findBiddingByUserUserName(anyString())).willReturn(prepareBidding());
        given(biddingRepository.findById(anyLong())).willReturn(prepareBidding().stream().findAny());
        given(biddingRepository.save(any())).willReturn(new Bidding());
    }


    @Test
    void getAll() {
        List<Bidding> all = biddingService.getAll();
        Assert.assertThat(all, hasSize(3));
    }

    @Test
    void getAllBiddingByUser() {
        List<Bidding> allBiddingByUser = biddingService.getAllBiddingByUser(anyString());
        Assert.assertThat(allBiddingByUser, hasSize(3));

    }

    @Test
    void getBidding() {
        Bidding bidding = biddingService.getBidding(anyLong());
        Assert.assertNotNull(bidding);
    }

    @Test
    void crateBidding() {
        Bidding bidding = biddingService.crateBidding(new BiddingDto(), new User());
        assertNotNull(bidding);

    }

    @Test
    void updatePrice() {
//        Bidding bidding = biddingService.updatePrice(new User(), 20.00, anyLong());
    }

    private List<Bidding> prepareBidding(){
        return Arrays.asList(
                new Bidding(),
                new Bidding(),
                new Bidding()
        );
    }
}