package com.machineCoding.bookmyshow.repositories;

import com.machineCoding.bookmyshow.models.Show;
import com.machineCoding.bookmyshow.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    @Override
    List<ShowSeat> findAllById(Iterable<Long> longs);

    @Override
    ShowSeat save(ShowSeat showSeat);


}
