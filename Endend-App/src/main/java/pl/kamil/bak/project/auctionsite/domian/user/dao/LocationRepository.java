package pl.kamil.bak.project.auctionsite.domian.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamil.bak.project.auctionsite.model.userEntity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
