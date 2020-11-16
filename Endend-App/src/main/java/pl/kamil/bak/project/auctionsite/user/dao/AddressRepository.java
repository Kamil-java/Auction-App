package pl.kamil.bak.project.auctionsite.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamil.bak.project.auctionsite.user.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
