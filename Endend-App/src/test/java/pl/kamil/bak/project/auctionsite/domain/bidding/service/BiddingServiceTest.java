package pl.kamil.bak.project.auctionsite.domain.bidding.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.kamil.bak.project.auctionsite.domain.bidding.dao.BiddingRepository;
import pl.kamil.bak.project.auctionsite.domain.bidding.dto.BiddingDto;
import pl.kamil.bak.project.auctionsite.model.biddingEntity.Bidding;
import pl.kamil.bak.project.auctionsite.model.userEntity.User;


import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BiddingServiceTest {

    @Mock
    private BiddingRepository biddingRepository;

    @Mock
    private ModelMapper modelMapper;


    private BiddingService biddingService;

    @BeforeEach
    public void init() {
        biddingService = new BiddingService(biddingRepository, modelMapper);
    }


    @Test
    void getAll() {
        given(biddingRepository.findAll()).willReturn(prepareBidding());
        List<Bidding> all = biddingService.getAll();
        assertThat(all).hasSize(3);

    }

    @Test
    void getAllBiddingByUser() {
        given(biddingRepository.findBiddingByUserUserName(anyString())).willReturn(prepareBidding());
        List<Bidding> allBiddingByUser = biddingService.getAllBiddingByUser(anyString());
        assertThat(allBiddingByUser).hasSize(3);

    }

    @Test
    void getBidding() {
        given(biddingRepository.findById(anyLong())).willReturn(prepareBidding().stream().findAny());
        Bidding bidding = biddingService.getBidding(anyLong());
        assertNotNull(bidding);
    }

    @Test
    void crateBidding() {
        given(biddingRepository.save(any())).willReturn(new Bidding());
        Bidding bidding = biddingService.crateBidding(new BiddingDto(), new User());
        assertNotNull(bidding);

    }

    @Test
    void updatePrice() {
//        Bidding bidding = biddingService.updatePrice(new User(), 20.00, anyLong());
    }

    private List<Bidding> prepareBidding() {
        return Arrays.asList(
                new Bidding(),
                new Bidding(),
                new Bidding()
        );
    }
}