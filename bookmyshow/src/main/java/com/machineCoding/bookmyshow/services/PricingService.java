package com.machineCoding.bookmyshow.services;

import com.machineCoding.bookmyshow.models.Show;
import com.machineCoding.bookmyshow.models.ShowSeat;
import com.machineCoding.bookmyshow.models.ShowSeatType;
import com.machineCoding.bookmyshow.repositories.ShowSeatRepository;
import com.machineCoding.bookmyshow.repositories.ShowSeatTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PricingService {
    private ShowSeatTypeRepository showSeatTypeRepository;
    public int calculatePrice(List<ShowSeat> showSeatList, Show show)
    {
        List< ShowSeatType> showSeatTypes = showSeatTypeRepository.findAllByShow(show);
        int amount = 0;
        for(ShowSeat showSeat : showSeatList)
        {
            for(ShowSeatType showSeatType : showSeatTypes)
            {
                if(showSeat.getSeat().getSeatType().equals(showSeatType.getSeatType()))
                {
                    amount+=showSeatType.getPrice();
                    break;
                }
            }
        }
        return  amount;
    }
}
